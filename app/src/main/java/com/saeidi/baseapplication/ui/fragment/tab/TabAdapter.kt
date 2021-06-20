package com.saeidi.baseapplication.ui.fragment.tab

interface TabAdapter {
    fun getCount(): Int
    fun getIcon(position: Int): Int?
    fun getTitle(position: Int): String?
}