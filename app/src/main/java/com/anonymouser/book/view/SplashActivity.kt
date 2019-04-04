package com.anonymouser.book.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import com.anonymouser.book.R
import com.anonymouser.book.bean.User
import com.anonymouser.book.utlis.SPUtil
import com.anonymouser.book.view.home.HomeActivity
import com.anonymouser.book.view.login.LoginActivity
import com.orhanobut.logger.Logger
import io.reactivex.Observable

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            login()
        }, 1000)
    }

    private fun login() {
        if (SPUtil.getString("username","") == "") {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            return
        }
        User.loginByAccount(SPUtil.getString("username", ""),
            SPUtil.getString("password", ""),
            object : LogInListener<User>() {
                @SuppressLint("CheckResult")
                override fun done(user: User, exception: BmobException?) {
                    Observable.create<User> { observableEmitter ->
                        if (exception == null) {
                            observableEmitter.onNext(user)
                        } else {
                            observableEmitter.onError(exception)
                        }
                    }
                        .subscribe({
                            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                            finish()
                        }, {
                            Logger.e(it, "")
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            finish()
                        })
                }
            })
    }
}
