package com.anonymouser.book.view.login

import android.content.Intent
import com.anonymouser.base.BaseActivity
import com.anonymouser.book.R
import com.anonymouser.book.view.home.HomeActivity
import com.anonymouser.book.view.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {

    override fun initView() {
    }

    override fun registerEvent() {
        btLogin.setOnClickListener {
            mPresenter?.login(etUsername?.text.toString(), etPassword?.text.toString())
        }
        btRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    override fun unRegisterEvent() {
    }

    override fun getPresenter() = LoginPresenter(this)
    override fun getLayoutId() = R.layout.activity_login

    override fun loginSucceed() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
}
