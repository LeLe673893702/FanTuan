package com.anonymouser.book.view.register

import android.annotation.SuppressLint
import android.text.TextUtils
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.anonymouser.book.bean.User
import com.anonymouser.book.utlis.RxUtil
import com.anonymouser.book.utlis.SPUtil
import com.anonymouser.book.utlis.ToastUtil
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 *
 * @what
 * @author
 * @date 2018/11/19
 *
 */
@SuppressLint("CheckResult")
class RegisterPresenter(private val mView: RegisterContract.View) : RegisterContract.Presenter {
    override fun loadData() {
    }


    override fun onDestroy() {
    }


    override fun register(username: String, password: String) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.getInstance().showToast("用户名不能为空")
            return
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.getInstance().showToast("密码不能为空")
            return
        }

        Observable.create(ObservableOnSubscribe<Any> { observableEmitter ->
            val userBean = User()
            userBean.nickname = username
            userBean.username = username
            userBean.setPassword(password)
            userBean.signUp(object : SaveListener<User>() {
                override fun done(userBean: User, e: BmobException?) {
                    if (e == null) {
                        observableEmitter.onNext(userBean)
                    } else {
                        observableEmitter.onError(e)
                    }
                    observableEmitter.onComplete()
                }
            })
        }).compose(RxUtil.loading(mView.attainActivity(), "正在注册中请稍后"))
            .compose(RxUtil.schedulersTransformer())
            .doOnError { throwable -> Logger.e(throwable.toString()) }
            .subscribe({
                SPUtil.putString("username", username)
                SPUtil.putString("password", password)
                ToastUtil.getInstance().showToast("注册成功, 请重新登陆")
                mView.registerSucceed()
            }, { ToastUtil.getInstance().showToast("注册失败") })
    }

}