package com.saeidi.baseapplication.ui.fragment.base.tab

import androidx.fragment.app.Fragment

class Tab {
    var icon: Int? = null
    var title: String? = null
    lateinit var fragment: Fragment

    constructor(title: String, fragment: Fragment) : this(title, null, fragment)

    constructor(icon: Int, fragment: Fragment) : this(null, icon, fragment)

    constructor(title: String?, icon: Int?, fragment: Fragment) {
        this.title = title
        this.fragment = fragment
        this.icon = icon
    }
}