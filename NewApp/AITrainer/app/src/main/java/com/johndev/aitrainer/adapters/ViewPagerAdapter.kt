package com.johndev.aitrainer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, private val listViews: List<Pair<Fragment, String>>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int  = listViews.size

    override fun getItem(position: Int): Fragment = listViews[position].first

    override fun getPageTitle(position: Int): CharSequence = listViews[position].second
}