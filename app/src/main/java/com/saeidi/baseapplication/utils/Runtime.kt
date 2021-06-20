package com.saeidi.baseapplication.utils

import android.os.Handler
import android.os.Looper

object Runtime {
    private val handler = Handler(Looper.getMainLooper())

    fun postToMainThread(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelayedToMainThread(runnable: Runnable, delayMillis: Long) {
        handler.postDelayed(runnable, delayMillis)
    }

    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}