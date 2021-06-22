package com.saeidi.baseapplication.ui.activity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.saeidi.baseapplication.Application
import com.saeidi.baseapplication.Constants
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.LocalDatabase
import com.saeidi.baseapplication.ui.fragment.base.tab.SimpleTabAdapter
import com.saeidi.baseapplication.ui.fragment.base.tab.Tab
import com.saeidi.baseapplication.ui.fragment.base.tab.TabsFragment
import com.saeidi.baseapplication.ui.fragment.root.*
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Intents
import com.saeidi.baseapplication.utils.LayoutUtil
import com.saeidi.baseapplication.utils.Style
import kotlinx.android.synthetic.main.activity_root_layout.*
import kotlinx.android.synthetic.main.dialog_select_font_size.view.*
import kotlinx.android.synthetic.main.dialog_select_lang.view.*
import kotlinx.android.synthetic.main.nav_header_root.view.*
import kotlinx.android.synthetic.main.root_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RootActivity : BaseFragmentActivity(), NavigationView.OnNavigationItemSelectedListener {

    init {
        canSwipe = false
    }

    private var navMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root_activity)

        // Configure Toolbar
        if (toolbar != null) {
            toolbarTitle.typeface = Fonts.bold()
            setSupportActionBar(toolbar)
        }
        supportActionBar?.elevation = 16f
        val rootFragment =
            TabsFragment.create(isBottom = true, isHorizontal = false)
        rootFragment.setRootFragment(true)
        val tabs = listOf(
            Tab(getString(R.string.recipes), R.drawable.ic_recipe, RecipesFragment()),
            Tab(getString(R.string.search), R.drawable.ic_search_cute, SearchFragment()),
            Tab(
                getString(R.string.what_to_cook),
                R.drawable.ic_fast_food,
                FoodDeterminationFragment()
            ),
            Tab(getString(R.string.profile), R.drawable.ic_profile, ProfileFragment())
        )
        val tabsAdapter = SimpleTabAdapter(supportFragmentManager, lifecycle, tabs)
        rootFragment.setAdapter(tabsAdapter)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, rootFragment)
            .commitNow()

        ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        ).apply {
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.ic_drawer)
            drawerArrowDrawable = DrawerArrowDrawable(this@RootActivity)
            toolbarNavigationClickListener = View.OnClickListener {
                drawerLayout.openDrawer(
                    GravityCompat.START
                )
            }
            drawerLayout.addDrawerListener(this)
            syncState()
        }
        navMenu = navigationView.menu
        navigationView.getChildAt(0).isVerticalScrollBarEnabled = false
        (navMenu!!.findItem(R.id.nav_night).actionView as CompoundButton).isChecked =
            Style.inNightMode(this)

        navigationView.getHeaderView(0).apply {
            nav_header_title.apply {
                typeface = Fonts.bold()
                setOnClickListener {
                    startActivity(Intents.openLogin(this@RootActivity))
                }
            }
        }

        navMenu!!.findItem(R.id.nav_version).title =
            "${getString(R.string.app_name)} ${getString(R.string.version)} ${
                LayoutUtil.formatNumber(
                    Application.getAppVersion(this)
                )
            }"

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        val nightMode = (navMenu!!.findItem(R.id.nav_night).actionView) as CompoundButton
        nightMode.setOnCheckedChangeListener { _, isChecked ->
            toggleNightMode(isChecked)
        }
    }

    override fun onBackPressed() {
        val rootFragment = supportFragmentManager.findFragmentById(R.id.root) as TabsFragment?
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (rootFragment != null) {
            if (rootFragment.canGoBack()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val close = false
        when (item.itemId) {
            R.id.nav_font_size -> {
                changeFontSize()
            }

            R.id.nav_language -> {
                changeLanguage()
            }

            R.id.nav_night -> {
                val nightMode = (navMenu!!.findItem(R.id.nav_night).actionView) as CompoundButton
                nightMode.isChecked = !nightMode.isChecked
            }

            R.id.nav_clear_cache -> {
                clearCache()
            }
        }
        if (close) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        return true
    }

    private fun toggleNightMode(night: Boolean) {
        Application.toggleNightMode(applicationContext, night)
        LayoutUtil.recreate(this)
    }

    private fun changeFontSize() {
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_select_font_size, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle(R.string.dialog_select_font_size_title)
        when (Application.currentFontScaleStyle) {
            R.style.FontStyle_Small -> dialogView.fontSize.check(R.id.small)
            R.style.FontStyle_Normal -> dialogView.fontSize.check(R.id.normal)
            R.style.FontStyle_Large -> dialogView.fontSize.check(R.id.large)
            R.style.FontStyle_XLarge -> dialogView.fontSize.check(R.id.xlarge)
        }
        val dialog = builder.create()
        dialogView.fontSize.setOnCheckedChangeListener { _, id: Int ->
            when (id) {
                R.id.small -> {
                    Application.setFontSize(
                        applicationContext,
                        Style.FontStyle.Small
                    )
                }
                R.id.normal -> {
                    Application.setFontSize(
                        applicationContext,
                        Style.FontStyle.Normal
                    )
                }
                R.id.large -> {
                    Application.setFontSize(
                        applicationContext,
                        Style.FontStyle.Large
                    )
                }
                R.id.xlarge -> {
                    Application.setFontSize(
                        applicationContext,
                        Style.FontStyle.Xlarge
                    )
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun changeLanguage() {
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.dialog_select_lang, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle(R.string.dialog_select_lang_title)
        when (LayoutUtil.getLanguage()) {
            LayoutUtil.Lang.FA -> dialogView.language.check(R.id.fa)
            LayoutUtil.Lang.EN -> dialogView.language.check(R.id.en)
        }
        val dialog = builder.create()
        dialogView.language.setOnCheckedChangeListener { _, id: Int ->
            if (id == R.id.en) {
                Application.setLocale(applicationContext, "en", "US", true)
            } else if (id == R.id.fa) {
                Application.setLocale(applicationContext, "fa", "IR", true)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun clearCache() {
        GlobalScope.launch {
            getSharedPreferences(Constants.CONFIG_PREF_NAME, MODE_PRIVATE).edit().clear().apply()
            LocalDatabase.getDatabase(this@RootActivity).deleteDatabase()
            Application.restart(applicationContext, 500)
        }
    }
}