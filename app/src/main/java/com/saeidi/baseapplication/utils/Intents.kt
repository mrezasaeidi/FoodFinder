package com.saeidi.baseapplication.utils

import android.app.Activity
import android.content.Intent
import com.saeidi.baseapplication.ui.activity.base.RouterActivity

object Intents {

    // Routing Params
    const val FRAGMENT_ID = "FRAGMENT_ID"
    const val PARAM_1 = "PARAM_1"
    const val PARAM_2 = "PARAM_2"
    const val PARAM_3 = "PARAM_3"
    const val PARAM_4 = "PARAM_4"
    const val PARAM_5 = "PARAM_5"

    // Fragments
    const val FOODS_FRAGMENT = 1
    const val FOOD_FRAGMENT = 2
    const val LOGIN_FRAGMENT = 3


    private fun createIntent(activity: Activity, cls: Class<*>?): Intent {
        val intent = Intent(activity, cls)
        if (activity.intent.extras != null) {
            intent.putExtras(activity.intent.extras!!)
        }
        return intent
    }

    private fun route(activity: Activity, fragmentId: Int): Intent {
        val intent = createIntent(activity, RouterActivity::class.java)
        intent.putExtra(FRAGMENT_ID, fragmentId)
        return intent
    }

    fun openCategory(activity: Activity, categoryId: Int): Intent {
        return route(activity, FOODS_FRAGMENT).apply {
            putExtra(PARAM_1, categoryId)
        }
    }

    fun openFood(activity: Activity, foodId: Int): Intent {
        return route(activity, FOOD_FRAGMENT).apply {
            putExtra(PARAM_1, foodId)
        }
    }

    fun openLogin(activity: Activity): Intent {
        return route(activity, LOGIN_FRAGMENT)
    }
}