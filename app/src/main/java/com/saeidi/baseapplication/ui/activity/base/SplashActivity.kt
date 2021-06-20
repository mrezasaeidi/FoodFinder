package com.saeidi.baseapplication.ui.activity.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.saeidi.baseapplication.Constants
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Runtime
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        splashTitleTV.apply {
            setTextColor(Color.BLACK)
            typeface = Fonts.extraBold()
        }
        Runtime.postDelayedToMainThread({
            startActivity(Intent(this, RootActivity::class.java))
            finish()
        }, Constants.SPLASH_TIME_OUT)
    }
}