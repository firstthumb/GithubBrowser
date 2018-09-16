package com.ekocaman.app.githubbrowser.ui.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ekocaman.app.githubbrowser.ui.home.like.LikeFragment
import com.ekocaman.app.githubbrowser.ui.home.search.SearchFragment
import java.security.InvalidParameterException

class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> SearchFragment.newInstance()
            1 -> LikeFragment.newInstance()
            else -> throw InvalidParameterException("Unknown PageViewer Index")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Search"
            1 -> "Liked"
            else -> throw InvalidParameterException("Unknown PageViewer Index")
        }
    }
}
