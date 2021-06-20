package com.saeidi.baseapplication.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.view.Surface
import android.view.WindowManager
import com.saeidi.baseapplication.AndroidContext

object Screen {
    var density = AndroidContext.context.resources.displayMetrics.density
    private var scaledDensity = AndroidContext.context.resources.displayMetrics.scaledDensity

    fun dp(dp: Float): Int {
        return (dp * density + .5f).toInt()
    }

    fun sp(sp: Float): Int {
        return (sp * scaledDensity + .5f).toInt()
    }

    fun getWidth(): Int {
        return AndroidContext.context.resources.displayMetrics.widthPixels
    }

    fun getHeight(): Int {
        return AndroidContext.context.resources.displayMetrics.heightPixels
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId: Int = AndroidContext.context.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = AndroidContext.context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getNavbarHeight(): Int {
        if (hasNavigationBar()) {
            val resourceId: Int = AndroidContext.context.resources
                .getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return AndroidContext.context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return 0
    }

    fun hasNavigationBar(): Boolean {
        val resources: Resources = AndroidContext.context.resources
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    private var prevOrientation = -10

    @SuppressLint("WrongConstant", "SourceLockedOrientationActivity")
    fun lockOrientation(activity: Activity?) {
        if (activity == null || prevOrientation != -10) {
            return
        }
        try {
            prevOrientation = activity.requestedOrientation
            val manager = activity.getSystemService(Activity.WINDOW_SERVICE) as? WindowManager
            if (manager?.defaultDisplay != null) {
                val rotation = manager.defaultDisplay.rotation
                val orientation = activity.resources.configuration.orientation
                if (rotation == Surface.ROTATION_270) {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    }
                } else if (rotation == Surface.ROTATION_90) {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    } else {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                } else if (rotation == Surface.ROTATION_0) {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    } else {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }
                } else {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    } else {
                        activity.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun unlockOrientation(activity: Activity?) {
        if (activity == null) {
            return
        }
        try {
            if (prevOrientation != -10) {
                activity.requestedOrientation = prevOrientation
                prevOrientation = -10
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}