package com.anonymouser.book.view.register


import com.anonymouser.base.BasePresenter
import com.newler.putonghuapractice.base.BaseView


interface RegisterContract {
    interface Presenter: BasePresenter {
        fun register(username: String, password: String)
    }

    interface View:BaseView {
        fun registerSucceed()
    }
}
