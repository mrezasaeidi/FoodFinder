package com.saeidi.baseapplication.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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


    private fun createIntent(activity: AppCompatActivity, cls: Class<*>?): Intent {
        val intent = Intent(activity, cls)
        if (activity.intent.extras != null) {
            intent.putExtras(activity.intent.extras!!)
        }
        return intent
    }

    private fun route(activity: AppCompatActivity, fragmentId: Int): Intent {
        val intent = createIntent(activity, RouterActivity::class.java)
        intent.putExtra(FRAGMENT_ID, fragmentId)
        return intent
    }
}