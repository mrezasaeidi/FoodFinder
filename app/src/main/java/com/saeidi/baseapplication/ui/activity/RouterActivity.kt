package com.saeidi.baseapplication.ui.activity

import android.os.Bundle
import com.saeidi.baseapplication.utils.Intents.FRAGMENT_ID

class RouterActivity : BaseFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent!!.getIntExtra(FRAGMENT_ID, 0)) {

        }
    }
}