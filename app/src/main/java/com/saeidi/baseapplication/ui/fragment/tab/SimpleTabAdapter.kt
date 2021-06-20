package com.saeidi.baseapplication.ui.fragment.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SimpleTabAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val items: List<Tab>
) : FragmentStateAdapter(
    fragmentManager, lifecycle
) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position].fragment
    }

    fun getIcon(position: Int): Int? {
        return items[position].icon
    }

    fun getTitle(position: Int): String? {
        return items[position].title
    }
}