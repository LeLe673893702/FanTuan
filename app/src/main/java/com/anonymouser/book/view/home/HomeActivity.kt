package com.anonymouser.book.view.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.anonymouser.book.R
import com.anonymouser.book.bean.DownloadBookEvent
import com.anonymouser.book.event.CacheProgressEvent
import com.anonymouser.book.event.CheckUpdateEvent
import com.anonymouser.book.event.SaveIsShowAdInfo
import com.anonymouser.book.service.DownloadService
import com.anonymouser.book.utlis.AppUpdate
import com.anonymouser.book.view.home.bookcase.BookcaseFragment
import com.anonymouser.book.view.home.bookcategory.CategoryFragment
import com.anonymouser.book.view.home.bookrank.HotListFragment
import com.anonymouser.book.view.home.mine.MineFragment
import com.anonymouser.book.view.search.SearchActivity
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by YandZD on 2017/7/13.
 */
class HomeActivity : AppCompatActivity() {

    val HIDE_FIRST_LOGO = 0x0001;
    var mPresenter = HomePresenter()
    var mDownloadService = DownloadService()
    var mCheckUpdateEvent: CheckUpdateEvent? = null
    private val tabIdS = arrayOf(R.id.tabCollect, R.id.tabBookStore, R.id.tabRank,R.id.tabMine)

    private val onPageChangeListener: ViewPager.OnPageChangeListener by lazy {
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(pos: Int) {
                bnvHome.selectedItemId = tabIdS[pos]
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        }
    }
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == HIDE_FIRST_LOGO) {
                firstLogo.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_home)
        mHandler.sendEmptyMessageDelayed(HIDE_FIRST_LOGO, 2000)

        mPresenter.initJar()

        //删除临时数据
        mPresenter.removeTempDataBase()

        val viewPager = findViewById(R.id.viewPager) as ViewPager

        val creator = FragmentPagerItems.with(this)
        creator.add(resources.getString(R.string.book_case), BookcaseFragment::class.java)
        creator.add("书城", CategoryFragment::class.java)
        creator.add("排行榜", HotListFragment::class.java)
        creator.add("我的", MineFragment::class.java)

        val adapter = FragmentPagerItemAdapter(
                supportFragmentManager, creator.create())

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(onPageChangeListener)
        viewPager.offscreenPageLimit = 2

        EventBus.getDefault().register(this)

        bnvHome.setOnNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener,
                BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                viewPager.currentItem = when (menuItem.itemId) {
                    R.id.tabCollect -> 0
                    R.id.tabBookStore -> 1
                    R.id.tabRank -> 2
                    else -> 3
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter.notfiyBookCase()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        viewPager.removeOnPageChangeListener(onPageChangeListener)
        super.onDestroy()
    }

    fun onSearch(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cacheEvent(event: CacheProgressEvent) {
        if (event.isFinish)
            tvCacheProgress.visibility = View.GONE
        else
            tvCacheProgress.visibility = View.VISIBLE
        tvCacheProgress.text = "缓存中：${event.msg}"
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onDownloadEvent(downloadBookEvent: DownloadBookEvent) {
        mDownloadService.onDownloadEvent(downloadBookEvent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckUpdateEvent(event: CheckUpdateEvent): Boolean {
        mCheckUpdateEvent = event
        var update = AppUpdate(this)
        return update.getVersionInfo(mCheckUpdateEvent?.mBean)
    }

    @Subscribe
    fun onSaveIsShowAd(isShowAdInfo: SaveIsShowAdInfo) {
        getSharedPreferences("info", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("isShowAd", isShowAdInfo.isShowAd)
                .commit()
    }


}