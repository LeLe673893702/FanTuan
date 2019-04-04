package com.anonymouser.book.view.home.mine


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.anonymouser.book.R
import com.anonymouser.book.bean.User
import com.anonymouser.book.view.userinfo.UserInfoActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        registerEvent()
    }

    private fun initView() {
        val user = User.getCurrentUser(User::class.java)
        Glide.with(context).load(user.avatar?:"").apply(RequestOptions().circleCrop().error(R.drawable.app_logo))
                .into(ivAvatar)
        tvNickname.text = user.nickname
        tvCreateDate.text = user.createdAt.substring(0,10)
    }

    private fun registerEvent() {
        clPersonalInfo.setOnClickListener {
            val intent = Intent(activity, UserInfoActivity::class.java)
            startActivity(intent)
        }

        llAbout.setOnClickListener {
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
        }

        llCheckUpgrade.setOnClickListener {
            Toast.makeText(context, "已经是最新版了", Toast.LENGTH_LONG).show()
        }

        llNavFeedback.setOnClickListener {
            val intent = Intent(activity, FeedbackActivity::class.java)
            startActivity(intent)
        }

        llShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TEXT, "http://yourbuffslonnol.com")
            intent.putExtra(Intent.EXTRA_TITLE, resources.getString(R.string.app_name))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "请选择"))
        }

        llTeach.setOnClickListener {
            val intent = Intent(activity, UseTeachActivity::class.java)
            startActivity(intent)
        }
    }
}
