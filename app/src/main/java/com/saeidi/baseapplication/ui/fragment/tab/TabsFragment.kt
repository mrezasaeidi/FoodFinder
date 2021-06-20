package com.saeidi.baseapplication.ui.fragment.tab

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.TextViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.ui.fragment.BaseFragment
import com.saeidi.baseapplication.utils.*

class TabsFragment : BaseFragment() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var adapter: TabAdapter? = null
    private var listener: OnTabSelectedListener? = null
    private var counters: IntArray? = null
    private var withBadge = false
    private var defaultTab = 0

    @ColorRes
    private var textIconColor = 0

    companion object {
        private const val DEFAULT_TAB = "defaultTab"

        fun create(scrollable: Boolean, isTop: Boolean, offPageLimit: Int): TabsFragment {
            val fragment = TabsFragment()
            val args = Bundle()
            args.putBoolean("scrollable", scrollable)
            args.putBoolean("isTop", isTop)
            args.putInt("offPageLimit", offPageLimit)
            fragment.arguments = args
            return fragment
        }

        fun create(scrollable: Boolean, isTop: Boolean): TabsFragment {
            val fragment = TabsFragment()
            val args = Bundle()
            args.putBoolean("scrollable", scrollable)
            args.putBoolean("isTop", isTop)
            fragment.arguments = args
            return fragment
        }

        fun create(
            @ColorRes textIconColor: Int,
            scrollable: Boolean,
            isTop: Boolean
        ): TabsFragment {
            val fragment = TabsFragment()
            val args = Bundle()
            args.putInt("textIconColor", textIconColor)
            args.putBoolean("scrollable", scrollable)
            args.putBoolean("isTop", isTop)
            fragment.arguments = args
            return fragment
        }

        fun create(scrollable: Boolean, defaultTab: Int): TabsFragment {
            val fragment = TabsFragment()
            val args = Bundle()
            args.putBoolean("scrollable", scrollable)
            args.putInt(DEFAULT_TAB, defaultTab)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val res: View
        var arguments = arguments
        if (arguments == null) {
            arguments = Bundle()
        }
        res = if (arguments.getBoolean("isTop", true)) {
            inflater.inflate(R.layout.fragment_tabs_top, container, false)
        } else {
            inflater.inflate(R.layout.fragment_tabs, container, false)
        }
        tabLayout = res.findViewById(R.id.tab_layout)
        if (listener != null) {
            tabLayout?.addOnTabSelectedListener(listener!!)
        }
        if (arguments.getBoolean("scrollable", false)) {
            tabLayout?.tabMode = TabLayout.MODE_SCROLLABLE
        } else {
            tabLayout?.tabMode = TabLayout.MODE_FIXED
            tabLayout?.tabGravity = TabLayout.GRAVITY_FILL
        }
        defaultTab = arguments.getInt(DEFAULT_TAB, 0)
        textIconColor = arguments.getInt("textIconColor", 0)
        viewPager = res.findViewById(R.id.pager)
        viewPager?.offscreenPageLimit = arguments.getInt("offPageLimit", 3)
        onAdapterChanged()
        return res
    }


    fun setAdapter(adapter: TabAdapter) {
        this.adapter = adapter
        counters = IntArray(adapter.getCount())
        onAdapterChanged()
    }

    fun getCurrentPosition(): Int {
        return viewPager?.currentItem ?: -1
    }

    private fun onAdapterChanged() {
        if (adapter != null && viewPager != null) {
            viewPager?.postDelayed({
                if (isAdded) {
                    viewPager?.adapter = adapter as PagerAdapter?
                    tabLayout!!.setupWithViewPager(viewPager)
                    setTabs()
                }
            }, 10)
        }
    }

    private fun setTabs() {
        updateTabs()
        viewPager?.currentItem = defaultTab
    }

    fun onTabSelected(listener: OnTabSelectedListener?) {
        this.listener = listener
    }

    fun updateTabs() {
        val count = adapter!!.getCount()
        for (i in 0 until count) {
            updateTab(i, adapter!!.getTitle(i), adapter!!.getIcon(i))
        }
    }


    fun updateCounter(index: Int, counter: Int) {
        if (counters != null) {
            counters!![index] = counter
        }
        if (tabLayout != null && withBadge) {
            val tab = tabLayout!!.getTabAt(index)
            if (tab != null && tab.customView != null) {
                val txtCounter = tab.customView!!.findViewById<TextView>(R.id.counter)
                if (txtCounter != null) {
                    if (counter > 0) {
                        txtCounter.visible()
                    } else {
                        txtCounter.gone()
                    }
                    if (counter < 999) {
                        txtCounter.text = LayoutUtil.formatNumber(counter)
                    } else {
                        txtCounter.text =
                            java.lang.String.format("+%s", LayoutUtil.formatNumber(999))
                    }
                }
            }
        }
    }

    private fun updateTab(index: Int, title: String?, icon: Int?) {
        if (tabLayout == null) {
            return
        }
        val tab = tabLayout!!.getTabAt(index)
        if (tab != null) {
            val customView = getCustomView(title, icon)
            if (customView != null) {
                val counter: AppCompatTextView? = customView.findViewById(R.id.counter)
                if (counter != null) {
                    if (withBadge) {
                        if (counters != null) {
                            if (counters!![index] > 0) {
                                counter.visible()
                            } else {
                                counter.gone()
                            }
                            if (counters!![index] < 999) {
                                counter.text = LayoutUtil.formatNumber(counters!![index])
                            } else {
                                counter.text =
                                    java.lang.String.format("+%s", LayoutUtil.formatNumber(999))
                            }
                        } else {
                            counter.gone()
                        }
                    } else {
                        counter.gone()
                    }
                }
                tab.customView = customView
            }
        }
    }

    private fun getCustomView(title: String?, icon: Int?): View? {
        if (context == null) {
            return null
        }
        val customView = ConstraintLayout(requireContext())
        val parentLayoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Screen.dp(48f))
        customView.layoutParams = parentLayoutParams
        val counterTV = AppCompatTextView(requireContext())
        counterTV.id = R.id.counter
        counterTV.gravity = Gravity.CENTER
        counterTV.textAlignment = View.TEXT_ALIGNMENT_CENTER
        counterTV.textDirection = View.TEXT_DIRECTION_LTR
        counterTV.textSize = Style.getTextSizeUltralight(requireContext())
        counterTV.typeface = Fonts.regular()
        counterTV.background = Style.getAccentRectangle18dp(requireContext())
        counterTV.setTextColor(Color.WHITE)
        customView.addView(counterTV)
        val titleTV = AppCompatTextView(requireContext())
        titleTV.id = R.id.title
        titleTV.gravity = Gravity.CENTER
        titleTV.textAlignment = View.TEXT_ALIGNMENT_CENTER
        titleTV.textSize = Style.getTextSizeUltralight(requireContext())
        titleTV.typeface = Fonts.regular()
        if (textIconColor != 0) {
            titleTV.setTextColor(ContextCompat.getColor(requireContext(), textIconColor))
        }
        customView.addView(titleTV)
        val iconIV = AppCompatImageView(requireContext())
        iconIV.id = R.id.icon
        customView.addView(iconIV)
        val hasTitle: Boolean = !title.isNullOrEmpty()
        if (hasTitle) {
            titleTV.text = title
            titleTV.visible()
            iconIV.gone()
            if (icon != null) {
                if (LayoutUtil.rtl) {
                    titleTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
                } else {
                    titleTV.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
                }
                if (textIconColor != 0) {
                    TextViewCompat.setCompoundDrawableTintList(
                        titleTV,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                textIconColor
                            )
                        )
                    )
                }
                titleTV.compoundDrawablePadding = Screen.dp(4f)
            }
            val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            if (withBadge) {
                layoutParams.endToStart = counterTV.id
                val counterLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                counterLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                counterLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                counterLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                counterLayoutParams.startToEnd = titleTV.id
                counterLayoutParams.marginStart = Screen.dp(4f)
                counterTV.layoutParams = counterLayoutParams
            } else {
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            titleTV.layoutParams = layoutParams
        } else if (icon != null) {
            iconIV.visible()
            titleTV.gone()
            iconIV.setImageResource(icon)
            if (textIconColor != 0) {
                ImageViewCompat.setImageTintList(
                    iconIV,
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), textIconColor))
                )
            }
            val layoutParams: ConstraintLayout.LayoutParams =
                ConstraintLayout.LayoutParams(Screen.dp(24f), Screen.dp(24f))
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            if (withBadge) {
                layoutParams.startToEnd = counterTV.id
                val counterLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                counterLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                counterLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                counterLayoutParams.endToStart = iconIV.id
                counterTV.layoutParams = counterLayoutParams
            } else {
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            }
            iconIV.layoutParams = layoutParams
        }
        return customView
    }

    fun setWithBadge(withBadge: Boolean) {
        this.withBadge = withBadge
    }

    fun getAdapter(): TabAdapter? {
        return adapter
    }
}