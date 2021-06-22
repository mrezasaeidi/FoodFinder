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
import com.saeidi.baseapplication.storage.repository.local.entity.*
import com.saeidi.baseapplication.storage.repository.local.entity.Unit
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Runtime
import kotlinx.android.synthetic.main.splash_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

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
        var baseId = 1
        userDao.apply {
            insertOrUpdate(UserModel(baseId++, "نیکتا اکبرپور", null))
            insertOrUpdate(UserModel(baseId++, "سروش باجغلی", null))
            insertOrUpdate(UserModel(baseId++, "محمدرضا سعیدی", null))
            insertOrUpdate(UserModel(baseId++, "نیلوفر موجودی", null))
            insertOrUpdate(UserModel(baseId, "کیاناز نره‌ئی", null))
        }
    }

    private fun fillCategory(categoryDao: CategoryDao) {
        var baseId = 1
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
        var baseId = 1
        foodDao.apply {
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "کیمچی کره ای",
                    2,
                    getDummyCalorie(),
                    listOf("https://www.wearesovegan.com/wp-content/uploads/2020/09/howtomakevegankimchirecipe-h1.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("کاهوی چینی", null, null),
                        Material("نمک", null, null),
                        Material("آب", null, null),
                        Material("سیر", null, null),
                        Material("زنجبیل", null, null),
                        Material("سس ماهی", null, null),
                        Material("فلفل قرمز", null, null),
                        Material("ترب سفید", null, null),
                        Material("پیازچه", null, null),
                    ),
                    45,
                    getDummyDate(),
                    1,
                    15,
                    20
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "تاکو مکزیکی",
                    2,
                    getDummyCalorie(),
                    listOf("https://img.taste.com.au/w_-0kcUJ/taste/2016/11/aussie-style-beef-and-salad-tacos-86525-1.jpeg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("روغن زیتون", null, null),
                        Material("گوشت گاو", null, null),
                        Material("نمک", null, null),
                        Material("فلفل سیاه", null, null),
                        Material("سیر", null, null),
                        Material("پودر چیلی", null, null),
                        Material("زیره", null, null),
                        Material("پودر پیاز", null, null),
                        Material("سس گوجه فرنگی", null, null),
                        Material("آب مرغ", null, null),
                    ),
                    25,
                    getDummyDate(),
                    1,
                    52,
                    29
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سوشی",
                    2,
                    getDummyCalorie(),
                    listOf("https://tazzcdn.akamaized.net/uploads/cover/vinicius-benedit--1GEAA8q3wk-unsplash.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("برنج", null, null),
                        Material("سرکه برنج", null, null),
                        Material("شکر", null, null),
                        Material("نمک", null, null),
                        Material("جلبک نوری", null, null),
                        Material("خیار", null, null),
                        Material("آووکادو", null, null),
                        Material("گوشت ماهی", null, null),
                    ),
                    17,
                    getDummyDate(),
                    1,
                    17,
                    96
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "بیف استراگانف",
                    2,
                    getDummyCalorie(),
                    listOf("https://www.daringgourmet.com/wp-content/uploads/2014/12/Crock-Pot-Beef-Stroganoff-20.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("فیله گوشت", null, null),
                        Material("قارچ", null, null),
                        Material("خامه", null, null),
                        Material("کره", null, null),
                    ),
                    60,
                    getDummyDate(),
                    1,
                    42,
                    12
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "خورشت بادمجان",
                    1,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2019/09/%D8%AE%D9%88%D8%B1%D8%B4%D8%AA-%D8%A8%D8%A7%D8%AF%D9%85%D8%AC%D8%A7%D9%86-%D9%85%D8%AC%D9%84%D8%B3%DB%8C.jpg"),
                    listOf(
                        RecipeStep(
                            1,
                            "بادمجون ها رو پوست بگیرید بعد دو ساعت تو آب نمک خیس کنید بعد خشک کرده و سرخ کنید.\nاگه میخواهید مجلسی تر بشه میتونید به شکل زیر برش زده و سرخ کنید.",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-2.jpg"
                        ),
                        RecipeStep(
                            2,
                            "پیاز رو تفت بدید تا طلائی بشه",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-3.jpg"
                        ),
                        RecipeStep(
                            3,
                            "بعد گوشت رو بچینید روش",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-4.jpg"
                        ),
                        RecipeStep(
                            4,
                            "و گوشتها رو هم تفت بدید تا کمی سرخ بشن",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-5.jpg"
                        ),
                        RecipeStep(
                            5,
                            "آب رو با رب مخلوط کنید و بریزید رو گوشت",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-6.jpg"
                        ),
                        RecipeStep(
                            6,
                            "در ظرف رو ببنید همین که جوشید حرارت رو کم کنید تا گوشت پخته بشه حدود یک ونیم ساعت زمان میبره ( اگه گوشت گوساله باشه ) دراواخر پخت نمک رو اضافه کنید",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-7.jpg"
                        ),
                        RecipeStep(
                            7,
                            "وقتی گوشت پخته شد و حدود یک استکان آب تهش بود از رو گاز بردارید. بادمجونها رو بچینید ته ماهی تابه. گوشتها رو بزارید روش، آب گوشت رو با زعفران و آبغوره مخلوط کنید و بریزید رو خورشت، گوجه فرنگی ها رو هم نصف کرده و رو خورشت بچینید",
                            "https://www.tasvirezendegi.com/wp-content/uploads/2016/01/Eggplant-Stew-8.jpg"
                        ),
                        RecipeStep(
                            8,
                            "حدو یک ربع روی حرارت متوسط قرار بدید تا آب خورشت کشیده شده و به روغن بیفته. خورشت خوشمزه ما آماده ست ، میتوانید آن را با نان یا برنج سرو کنید.",
                            null
                        ),
                    ),
                    4,
                    listOf(
                        Material("بادمجان", 500f, Unit.GRAM),
                        Material("گوشت", 500f, Unit.GRAM),
                        Material("گوجه فرنگی", 3f, Unit.PIECE),
                        Material("پیاز", 1f, Unit.PIECE),
                        Material("زعفران", null, null),
                        Material("نمک", null, null),
                        Material("روغن", null, null),
                        Material("رب گوجه فرنگی", 2f, Unit.SPOON),
                        Material("آب", 4f, Unit.MODULE),
                        Material("آبغوره", 0.5f, Unit.MODULE),
                    ),
                    180,
                    getDummyDate(),
                    2,
                    68,
                    24
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "دلمه با برنج گل کلم",
                    1,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2020/02/%D8%AF%D9%84%D9%85%D9%87-%D9%81%D9%84%D9%81%D9%84.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("فلفل دلمه ای", null, null),
                        Material("برنج گل کلم", null, null),
                        Material("لوبیا قرمز", null, null),
                        Material("سس سالسا", null, null),
                        Material("پودر زیره", null, null),
                        Material("آب لیمو", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                    ),
                    60,
                    getDummyDate(),
                    2,
                    52,
                    80
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "هواری میگو",
                    1,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2019/09/%D9%87%D9%88%D8%A7%D8%B1%DB%8C_-%D9%85%DB%8C%DA%AF%D9%88-min.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("میگو", null, null),
                        Material("برنج", null, null),
                        Material("پیاز", null, null),
                        Material("سیر", null, null),
                        Material("گشنیز", null, null),
                        Material("شنبلیله", null, null),
                        Material("رب گوجه", null, null),
                        Material("کره", null, null),
                        Material("روغن", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("زردچوبه", null, null),
                        Material("زعفران", null, null),
                        Material("آب", null, null),
                    ),
                    70,
                    getDummyDate(),
                    2,
                    95,
                    25
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "حلیم بادمجان",
                    1,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2012/08/%D8%AD%D9%84%DB%8C%D9%85-%D8%A8%D8%A7%D8%AF%D9%85%D8%AC%D8%A7%D9%86.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("گوشت گوسفند", null, null),
                        Material("لوبیا سفید", null, null),
                        Material("بادمجان", null, null),
                        Material("برنج", null, null),
                        Material("پیاز داغ", null, null),
                        Material("کشک", null, null),
                        Material("زردچوبه", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("زعفران", null, null),
                        Material("نعنا داغ", null, null),
                    ),
                    150,
                    getDummyDate(),
                    2,
                    84,
                    54
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سالاد ماکارونی",
                    4,
                    getDummyCalorie(),
                    listOf("https://images.kojaro.com/2021/2/2d619874-0b5a-4080-b4e0-bcc5a8b5e619.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("ماکارونی", null, null),
                        Material("گوشت", null, null),
                        Material("سوسیس", null, null),
                        Material("کالباس", null, null),
                        Material("پنیر", null, null),
                    ),
                    30,
                    getDummyDate(),
                    3,
                    66,
                    23
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سالاد سزار",
                    4,
                    getDummyCalorie(),
                    listOf("https://images.kojaro.com/2021/2/266926bf-aa35-4903-a191-8e223a8959f3.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("مرغ سوخاری", null, null),
                        Material("کاهو", null, null),
                        Material("نان تست", null, null),
                        Material("پنیر پارمزان", null, null),
                        Material("آب لیمو", null, null),
                        Material("روغن زیتون", null, null),
                        Material("تخم مرغ", null, null),
                        Material("سس ووسترشایر", null, null),
                        Material("کنسرو ماهی", null, null),
                        Material("سیر", null, null),
                        Material("خردل", null, null),
                        Material("فلفل سیاه", null, null),
                    ),
                    30,
                    getDummyDate(),
                    3,
                    24,
                    12
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سالاد اولیه",
                    4,
                    getDummyCalorie(),
                    listOf("https://images.kojaro.com/2021/2/5f277313-b718-48d6-a16f-0deea5acc56e.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("مرغ", null, null),
                        Material("کالباس", null, null),
                        Material("قارچ", null, null),
                        Material("خیارشور", null, null),
                        Material("پیاز", null, null),
                        Material("سیب زمینی", null, null),
                        Material("تخم مرغ", null, null),
                        Material(" آب مرغ", null, null),
                        Material("نخود فرنگی", null, null),
                        Material("هویج", null, null),
                        Material("نمک", null, null),
                        Material("فلفل سیاه", null, null),
                        Material("آب لیمو", null, null),
                    ),
                    45,
                    getDummyDate(),
                    3,
                    52,
                    63
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سالاد یونانی",
                    4,
                    getDummyCalorie(),
                    listOf("https://images.kojaro.com/2021/2/c9a26dbc-0287-49a2-a86b-06c8fd995639.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("خیار", null, null),
                        Material("کاهو", null, null),
                        Material("گوجه فرنگی", null, null),
                        Material("زیتون", null, null),
                        Material("پنیر فتا", null, null),
                        Material("سس سرکه ای", null, null),
                    ),
                    35,
                    getDummyDate(),
                    3,
                    10,
                    5
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سوپ گوجه فرنگی",
                    3,
                    getDummyCalorie(),
                    listOf("https://www.alamto.com/wp-content/uploads/2019/02/%D8%B3%D9%88%D9%BE-%DA%AF%D9%88%D8%AC%D9%87-%D9%81%D8%B1%D9%86%DA%AF%DB%8C.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("کره", null, null),
                        Material("آرد", null, null),
                        Material("گوجه فرنگی", null, null),
                        Material("نمک", null, null),
                        Material("فلفل سیاه", null, null),
                        Material("رب گوجه فرنگی", null, null),
                        Material("جعفری", null, null),
                        Material("آب گوشت", null, null),
                        Material("پیاز", null, null),
                    ),
                    20,
                    getDummyDate(),
                    4,
                    20,
                    16
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سوپ قارچ",
                    3,
                    getDummyCalorie(),
                    listOf("https://www.alamto.com/wp-content/uploads/2014/06/mushroom-soup.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("پیاز", null, null),
                        Material("سیر", null, null),
                        Material("قارچ", null, null),
                        Material("کرفس", null, null),
                        Material("کره", null, null),
                        Material("شیر", null, null),
                        Material("شیر خشک", null, null),
                        Material("آرد", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                    ),
                    20,
                    getDummyDate(),
                    4,
                    12,
                    5
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سوپ ذرت",
                    3,
                    getDummyCalorie(),
                    listOf("https://www.alamto.com/wp-content/uploads/2021/01/corn-soup-recipe.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("کنسرو ذرت", null, null),
                        Material("روغن", null, null),
                        Material("پیاز", null, null),
                        Material("سیر", null, null),
                        Material("فلفل سفید", null, null),
                        Material("نمك و فلفل", null, null),
                        Material("آب مرغ", null, null),
                        Material("خامه", null, null)
                    ),
                    20,
                    getDummyDate(),
                    4,
                    15,
                    4
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "سوپ کلم",
                    3,
                    getDummyCalorie(),
                    listOf("https://www.alamto.com/wp-content/uploads/2014/05/cabbage-soup.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("پیاز", null, null),
                        Material("کلم", null, null),
                        Material("هویج", null, null),
                        Material("روغن", null, null),
                        Material("کره", null, null),
                        Material("شکر", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("آب مرغ", null, null),
                        Material("شیر", null, null)
                    ),
                    20,
                    getDummyDate(),
                    4,
                    12,
                    1
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "املت ایتالیایی",
                    5,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2020/08/%D8%A7%D9%85%D9%84%D8%AA-%D8%A7%DB%8C%D8%AA%D8%A7%D9%84%DB%8C%D8%A7%DB%8C%DB%8C.png"),
                    emptyList(),
                    4,
                    listOf(
                        Material("تخم مرغ", null, null),
                        Material("جعفری", null, null),
                        Material("پودر پنیر پارمزان", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("روغن زیتون", null, null)
                    ),
                    10,
                    getDummyDate(),
                    5,
                    16,
                    8
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "عدسی",
                    5,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2017/12/Adasi-Khorak.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("عدس", null, null),
                        Material("پیاز", null, null),
                        Material("رب گوجه فرنگی", null, null),
                        Material("زردچوبه", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("سیب زمینی", null, null),
                        Material("روغن زیتون", null, null),
                        Material("گندم", null, null),
                    ),
                    105,
                    getDummyDate(),
                    5,
                    25,
                    72
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "تخم مرغ روی چیپس",
                    5,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2020/01/%D8%AA%D8%AE%D9%85-%D9%85%D8%B1%D8%BA-%DA%86%DB%8C%D9%BE%D8%B3.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("تخم مرغ", null, null),
                        Material("چیپس سیب زمینی", null, null),
                        Material("پیاز", null, null),
                        Material("فلفل سبز", null, null),
                        Material("زنجبیل", null, null),
                        Material("جعفری", null, null),
                    ),
                    12,
                    getDummyDate(),
                    5,
                    15,
                    2
                )
            )
            insertOrUpdate(
                FoodModel(
                    baseId++,
                    "خوراک لوبیا چیتی",
                    5,
                    getDummyCalorie(),
                    listOf("https://irancook.ir/wp-content/uploads/2012/07/khorak-loobia-chiti-10.jpg"),
                    emptyList(),
                    4,
                    listOf(
                        Material("لوبيا چیتی", null, null),
                        Material("پیاز", null, null),
                        Material("رب گوجه فرنگی", null, null),
                        Material("سیب زمینی", null, null),
                        Material("نمک", null, null),
                        Material("فلفل", null, null),
                        Material("زردچوبه", null, null),
                    ),
                    180,
                    getDummyDate(),
                    5,
                    10,
                    24
                )
            )
        }
    }

    private fun getDummyDate(): Long {
        return Random().nextLong()
    }

    private fun getDummyCalorie(): Int {
        return Random().nextInt(15000)
    }
}