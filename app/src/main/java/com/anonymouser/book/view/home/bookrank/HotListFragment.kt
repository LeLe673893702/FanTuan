package com.anonymouser.book.view.home.bookrank;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anonymouser.book.R
import kotlinx.android.synthetic.main.fragment_hot_list.*

/**
 * Created by YandZD on 2017/7/13.
 */

class HotListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hot_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = RankPageAdapter(childFragmentManager)
        vpRank.adapter = adapter
        smartTabLayout.setViewPager(vpRank)
    }

    class RankPageAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(pos: Int): Fragment {
            return when(pos) {
                0->  BookRankFragment.newInstance(BookRankFragment.MALE)
                else -> BookRankFragment.newInstance(BookRankFragment.FEMALE)
            }

        }

        override fun getCount()=2

        override fun getPageTitle(position: Int): CharSequence? {
            return  when(position) {
                0-> "男生"
                else -> "女生"
            }
        }

    }
}
