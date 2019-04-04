package com.anonymouser.book.view.bookinfo

import com.anonymouser.base.BasePresenter
import com.anonymouser.book.bean.SearchBookInfoBean
import com.newler.putonghuapractice.base.BaseView

/**
 *
 * @what
 * @author 17173
 * @date 2019/4/3
 *
 */
interface BookInfoContract {
    interface View : BaseView {
        fun loadSucceed(bookInfo:SearchBookInfoBean)
        fun loadFailed()
    }

    interface Presenter : BasePresenter {

    }
}