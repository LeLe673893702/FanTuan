package com.anonymouser.book.view.userinfo.nickname

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.anonymouser.book.R
import com.anonymouser.book.bean.User
import com.anonymouser.book.event.BusAction
import com.anonymouser.book.utlis.RxUtil
import com.anonymouser.book.utlis.ToastUtil
import com.hwangjr.rxbus.RxBus
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_modify_nickname.*

class ModifyNicknameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_nickname)
        tvConfirm.setOnClickListener {
            updateNickname(etNewNickname.text.toString())
        }
    }

    @SuppressLint("CheckResult")
    fun updateNickname(nickname: String) {
        if (nickname.length > 15) {
            ToastUtil.getInstance().showToast("昵称太长，请设置不超过15个字的昵称")
            return
        }

        val userBean = User.getCurrentUser(User::class.java)
        userBean.nickname = nickname

        Observable.create(ObservableOnSubscribe<Any> { emitter ->
            userBean.update(object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        emitter.onNext(userBean)
                    } else {
                        emitter.onError(e)
                    }
                    emitter.onComplete()
                }
            })
        }).compose(RxUtil.schedulersTransformer())
            .compose(RxUtil.loading(this, "正在修改昵称"))
            .subscribe({
                RxBus.get().post(BusAction.UPDATE_NICKNAME, nickname)
                ToastUtil.getInstance().showToast("昵称修改成功")
            }, { ToastUtil.getInstance().showToast("昵称修改失败") })
    }
}
