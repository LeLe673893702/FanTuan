package com.newler.putonghuapractice.page.mine.personal

import com.anonymouser.book.event.BusAction
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread

/**
 *
 * @what
 * @author 17173
 * @date 2018/11/19
 *
 */
class UserInfoPresenter(private val mView: UserInfoContract.View) : UserInfoContract.Presenter {
    override fun onDestroy() {
    }

    override fun loadData() {
    }


    @Subscribe(thread = EventThread.MAIN_THREAD, tags  = [Tag(BusAction.UPDATE_NICKNAME)])
    fun updateNickname(nickName: String) {
        mView.updateNickname(nickName)
    }

}