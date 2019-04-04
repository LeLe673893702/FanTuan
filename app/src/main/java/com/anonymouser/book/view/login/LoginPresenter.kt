package com.anonymouser.book.view.login

import android.text.TextUtils
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import com.anonymouser.book.bean.User
import com.anonymouser.book.utlis.RxUtil
import com.anonymouser.book.utlis.SPUtil
import com.anonymouser.book.utlis.ToastUtil
import com.anonymouser.book.view.login.LoginContract
import com.orhanobut.logger.Logger
import io.reactivex.Observable

/**
 *
 * @what
 * @author
 * @date 2018/11/19
 *
 */
class LoginPresenter(private val mView: LoginContract.View) : LoginContract.Presenter {
    override fun loadData() {
    }

    override fun onDestroy() {
    }

    override fun login(username: String, password: String) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.getInstance().showToast("用户名不能为空")
            return
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.getInstance().showToast("密码不能为空")
            return
        }

        User.loginByAccount(username, password, object : LogInListener<User>() {
            override fun done(user: User, exception: BmobException?) {
                Observable.create<User> { observableEmitter ->
                    if (exception == null) {
                        observableEmitter.onNext(user)
                    } else {
                        observableEmitter.onError(exception)
                    }
                }
                        .compose(RxUtil.loading(mView.attainActivity(), "正在登陆中请稍后"))
                        .`as`(mView.disposeOnDestroy())
                        .subscribe({
                            SPUtil.putString("username", username)
                            SPUtil.putString("password", password)
                            ToastUtil.getInstance().showToast("登陆成功")
                            mView.loginSucceed()
                        }, {
                            Logger.e(it.toString())
                            ToastUtil.getInstance().showToast("用户名或密码错误")
                        })
            }
        })
    }


}