package com.saeidi.baseapplication.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.getColorCompat(colorId: Int): Int = ContextCompat.getColor(this, colorId)