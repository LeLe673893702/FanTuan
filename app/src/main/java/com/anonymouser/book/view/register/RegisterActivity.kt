package com.anonymouser.book.view.register

import android.content.Intent
import android.text.TextUtils
import com.anonymouser.base.BaseActivity
import com.anonymouser.book.R
import com.anonymouser.book.utlis.ToastUtil
import com.anonymouser.book.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterContract.Presenter>(), RegisterContract.View {
    override fun registerSucceed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun initView() {
    }

    override fun registerEvent() {

        btConfirm.setOnClickListener {
            if (!TextUtils.equals(etPassword.text.toString(), etNextPassword.text.toString())) {
                ToastUtil.getInstance().showToast("两次输入密码不同，请重新输入")
                return@setOnClickListener
            }
            mPresenter?.register(etUsername.text.toString(), etPassword.text.toString())
        }
    }

    override fun unRegisterEvent() {
    }

    override fun getPresenter() = RegisterPresenter(this)

    override fun getLayoutId() = R.layout.activity_register


}
