package com.anonymouser.book.view.userinfo

import android.content.Intent
import com.anonymouser.book.R
import com.anonymouser.book.bean.User
import com.anonymouser.base.BaseActivity
import com.anonymouser.book.view.login.LoginActivity
import com.anonymouser.book.view.userinfo.nickname.ModifyNicknameActivity
import com.anonymouser.book.view.userinfo.password.ModifyPasswordActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.newler.putonghuapractice.page.mine.personal.UserInfoPresenter
import com.newler.putonghuapractice.page.mine.personal.UserInfoContract
import kotlinx.android.synthetic.main.activity_presonal_info.*

class UserInfoActivity : BaseActivity<UserInfoContract.Presenter>(), UserInfoContract.View {
    override fun initView() {
        val user = User.getCurrentUser(User::class.java)
        tvNickname.text = user.nickname
        tvUsername.text = user.username
        Glide.with(this).load(user.avatar?:"").apply(RequestOptions().circleCrop().
                error(R.drawable.app_logo).placeholder(R.drawable.app_logo))
                .into(ivAvatar)

    }

    override fun registerEvent() {
        btLogOut.setOnClickListener {
            User.logOut()
            val intent = Intent(this@UserInfoActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        rlNickname.setOnClickListener {
            startActivity(Intent(this@UserInfoActivity, ModifyNicknameActivity::class.java))
        }
        rlPassword.setOnClickListener {
            startActivity(Intent(this@UserInfoActivity, ModifyPasswordActivity::class.java))
        }
    }

    override fun unRegisterEvent() {
    }

    override fun getPresenter() = UserInfoPresenter(this)

    override fun getLayoutId() = R.layout.activity_presonal_info

    override fun updateNickname(nickName: String) {
        tvNickname.text = nickName
    }
}
