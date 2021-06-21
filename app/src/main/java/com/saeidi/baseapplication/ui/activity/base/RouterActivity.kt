package com.saeidi.baseapplication.ui.activity.base

import android.os.Bundle
import com.saeidi.baseapplication.ui.fragment.FoodFragment
import com.saeidi.baseapplication.ui.fragment.FoodsFragment
import com.saeidi.baseapplication.utils.Intents.FOODS_FRAGMENT
import com.saeidi.baseapplication.utils.Intents.FOOD_FRAGMENT
import com.saeidi.baseapplication.utils.Intents.FRAGMENT_ID

class RouterActivity : BaseFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent!!.getIntExtra(FRAGMENT_ID, 0)) {
            FOODS_FRAGMENT -> showFragment(FoodsFragment(), false)
            FOOD_FRAGMENT -> showFragment(FoodFragment(), false)
        }
    }
}