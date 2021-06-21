package com.saeidi.baseapplication.ui.activity.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.saeidi.baseapplication.Constants
import com.saeidi.baseapplication.Constants.CONFIG_PREF_FIRST_TIME
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.LocalDatabase
import com.saeidi.baseapplication.storage.repository.local.dao.CategoryDao
import com.saeidi.baseapplication.storage.repository.local.dao.FoodDao
import com.saeidi.baseapplication.storage.repository.local.dao.UserDao
import com.saeidi.baseapplication.storage.repository.local.entity.CategoryModel
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel
import com.saeidi.baseapplication.storage.repository.local.entity.UserModel
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Runtime
import kotlinx.android.synthetic.main.splash_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        GlobalScope.launch(Dispatchers.IO) {
            val preferences = getSharedPreferences(Constants.CONFIG_PREF_NAME, MODE_PRIVATE)
            if (preferences.getBoolean(CONFIG_PREF_FIRST_TIME, true)) {
                fillDatabase()
                preferences.edit().putBoolean(CONFIG_PREF_FIRST_TIME, false).apply()
            }
            Runtime.postDelayedToMainThread({
                startActivity(Intent(this@SplashActivity, RootActivity::class.java))
                finish()
            }, Constants.SPLASH_TIME_OUT)
        }
    }

    private fun fillDatabase() {
        val db = LocalDatabase.getDatabase(this)
        fillUsers(db.userDao())
        fillCategory(db.categoryDao())
        fillFoods(db.foodDao())
    }

    private fun fillUsers(userDao: UserDao) {
        var baseId = 0
        userDao.apply {
            insertOrUpdate(UserModel(baseId++, "نیکتا اکبرپور", null))
            insertOrUpdate(UserModel(baseId++, "سروش باجغلی", null))
            insertOrUpdate(UserModel(baseId++, "محمدرضا سعیدی", null))
            insertOrUpdate(UserModel(baseId++, "نیلوفر موجودی", null))
            insertOrUpdate(UserModel(baseId, "کیاناز نره‌ئی", null))
        }
    }

    private fun fillCategory(categoryDao: CategoryDao) {
        var baseId = 0
        categoryDao.apply {
            insertOrUpdate(
                CategoryModel(
                    baseId++,
                    "سنتی",
                    "Traditional",
                    "https://www.alibaba.ir/mag/wp-content/uploads/2020/02/beyuni.jpg"
                )
            )
            insertOrUpdate(
                CategoryModel(
                    baseId++,
                    "مدرن",
                    "Modern",
                    "https://blog.okcs.com/wp-content/uploads/2019/11/a70b8fde37a14292f7d318fbe951fc7c.jpg"
                )
            )
            insertOrUpdate(
                CategoryModel(
                    baseId++,
                    "سوپ",
                    "Soup",
                    "https://ghazapaz.com/wp-content/uploads/2020/09/%D8%B7%D8%B1%D8%B2-%D8%AA%D9%87%DB%8C%D9%87-%D8%B3%D9%88%D9%BE-%D8%AC%D9%88.jpg"
                )
            )
            insertOrUpdate(
                CategoryModel(
                    baseId++,
                    "سالاد",
                    "Salad",
                    "https://blog.okcs.com/wp-content/uploads/2021/02/52744_3000x2000-scaled.jpg"
                )
            )
            insertOrUpdate(
                CategoryModel(
                    baseId,
                    "دانشجویی",
                    "Students",
                    "https://salamat.ir/uploads/posts/2020-05/1589301828_scramble.jpg"
                )
            )
        }
    }

    private fun fillFoods(foodDao: FoodDao) {
        var baseId = 0
//        foodDao.apply {
//            insertOrUpdate(FoodModel(baseId++,))
//        }
    }
}