package com.anonymouser.book.view.login


import com.anonymouser.base.BasePresenter
import com.newler.putonghuapractice.base.BaseView

interface LoginContract {
    interface Presenter: BasePresenter {
        fun login(username:String, password: String)
    }

    interface View:BaseView {
        fun loginSucceed()
    }
}
