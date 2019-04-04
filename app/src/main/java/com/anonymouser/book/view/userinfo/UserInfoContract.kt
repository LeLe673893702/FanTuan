package com.newler.putonghuapractice.page.mine.personal


import com.anonymouser.base.BasePresenter
import com.newler.putonghuapractice.base.BaseView


interface UserInfoContract {
    interface Presenter: BasePresenter {

    }

    interface View:BaseView {
        fun updateNickname(nickName:String)
    }
}
