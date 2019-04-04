package com.anonymouser.book.view.bookinfo

import android.text.TextUtils
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.anonymouser.base.BaseActivity
import com.anonymouser.book.R
import com.anonymouser.book.bean.SearchBookInfoBean
import com.anonymouser.book.event.AddBookCaseEvent
import com.anonymouser.book.module.BookModule
import com.anonymouser.book.utlis.ImgLoad
import com.anonymouser.book.view.read.ReadActivity
import com.anonymouser.book.view.read.ReadZhuiShuActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_book_info.*
import org.greenrobot.eventbus.EventBus

class BookInfoActivity : BaseActivity<BookInfoContract.Presenter>(), BookInfoContract.View {
    private val bookName by lazy {  intent.getStringExtra("bookName") ?: "" }
    private var bookInfo : SearchBookInfoBean? = null
    override fun initView() {
        pbLoading.visibility = View.VISIBLE
        llBookInfo.visibility = View.GONE
    }

    override fun registerEvent() {
        bt_read.setOnClickListener {view->
            bookInfo?.let {
                val bookCaseBean = BookModule.getBookCaseBean(it.bookName)

                if (bookCaseBean != null) {
                    if (bookCaseBean.isZhuiShu) {
                        startActivity(ReadZhuiShuActivity.newInstance(this@BookInfoActivity, bookCaseBean))
                    } else {
                        startActivity(ReadActivity.newInstance(this@BookInfoActivity, bookCaseBean))
                    }
                } else {
                    if (it.isZhuiShu) {
                        startActivity(ReadZhuiShuActivity.newInstance(this@BookInfoActivity, it))
                    } else {
                        startActivity(ReadActivity.newInstance(this@BookInfoActivity, it))
                    }
                }
            }

        }

        bt_add_bookcase.setOnClickListener {
            bookInfo?.let {
                bt_read.text = "已加入书架"
                bt_read.isEnabled = false
                val event = AddBookCaseEvent()
                event.setBeanFromSearchBookInfoBean(it)
                EventBus.getDefault().post(event)

                SweetAlertDialog(this@BookInfoActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("").show()

            }
        }

        ivReturn.setOnClickListener {
            finish()
        }
    }

    override fun unRegisterEvent() {
    }

    override fun getPresenter(): BookInfoContract.Presenter {
        return BookInfoPresenter(bookName, this)
    }

    override fun getLayoutId() = R.layout.activity_book_info

    override fun loadSucceed(bookInfo: SearchBookInfoBean) {
        this.bookInfo = bookInfo
        pbLoading.visibility = View.GONE
        llBookInfo.visibility = View.VISIBLE
        tvName.text = bookInfo.bookName
        tvAuthor.text = "作者：${bookInfo.author}"
        tvType.text = "类型：${bookInfo.type}"
        tvIntro.text = "简介：${bookInfo.intro}"
        ImgLoad.baseLoadImg(bookInfo.img, ivCover)
    }


    override fun loadFailed() {
        llBookInfo.visibility = View.GONE
        pbLoading.visibility  = View.GONE
    }
}
