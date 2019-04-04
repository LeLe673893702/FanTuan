package com.anonymouser.book.view.userinfo.password

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.anonymouser.book.R
import com.anonymouser.book.bean.User
import com.anonymouser.book.utlis.RxUtil
import com.anonymouser.book.utlis.ToastUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_modify_password.*

class ModifyPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
        tvConfirm.setOnClickListener {
            updatePassword()
        }
    }

    @SuppressLint("CheckResult")
    fun updatePassword() {
        Observable.create(ObservableOnSubscribe<Any> { emitter ->
            User.updateCurrentUserPassword(etOldPassword.text.toString(), etNewPassword.text.toString(), object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        emitter.onNext("")
                    } else {
                        emitter.onError(e)
                    }
                    emitter.onComplete()
                }
            })
        }).compose(RxUtil.schedulersTransformer())
            .compose(RxUtil.loading(this, "正在修改密码"))
            .subscribe({
                ToastUtil.getInstance().showToast("密码修改成功，可以用新密码进行登录啦")
            }, {
                ToastUtil.getInstance().showToast("密码修改失败，$it.message")
            })
    }
}
