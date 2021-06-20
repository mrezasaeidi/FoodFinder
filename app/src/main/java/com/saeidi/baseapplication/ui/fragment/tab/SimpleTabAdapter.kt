package com.saeidi.baseapplication.ui.fragment.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class SimpleTabAdapter(fm: FragmentManager, private val items: Array<Tab>) : TabFragmentAdapter(fm) {

    override fun getCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position].fragment
    }

    override fun getIcon(position: Int): Int? {
        return items[position].icon
    }

    override fun getTitle(position: Int): String? {
        return items[position].title
    }

}