package com.saeidi.baseapplication.ui.fragment.tab

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.PagerAdapter
import java.util.*

abstract class TabFragmentAdapter(private val mFragmentManager: FragmentManager) : PagerAdapter(), TabAdapter {

    private var mCurTransaction: FragmentTransaction? = null
    private val mSavedState = ArrayList<Fragment.SavedState?>()
    private val mFragments = ArrayList<Fragment?>()
    private var mCurrentPrimaryItem: Fragment? = null


    override fun startUpdate(container: ViewGroup) {
        if (container.id == View.NO_ID) {
            throw IllegalStateException("ViewPager with adapter $this requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        if (mFragments.size > position) {
            val f = mFragments[position]
            if (f != null) {
                return f
            }
        }
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }
        val fragment = createFragment(position)
        if (mSavedState.size > position) {
            val fss = mSavedState[position]
            if (fss != null) {
                fragment.setInitialSavedState(fss)
            }
        }
        while (mFragments.size <= position) {
            mFragments.add(null)
        }
        fragment.setMenuVisibility(false)
        fragment.userVisibleHint = false
        mFragments[position] = fragment
        mCurTransaction!!.add(container.id, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }

        while (mSavedState.size <= position) {
            mSavedState.add(null)
        }
        if (mFragments.size > position) {
            mSavedState[position] = if (fragment.isAdded) mFragmentManager.saveFragmentInstanceState(fragment) else null
            mFragments[position] = null
            mCurTransaction!!.remove(fragment)
        }
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment?
        if (fragment !== mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem!!.setMenuVisibility(false)
                mCurrentPrimaryItem!!.userVisibleHint = false
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true)
                fragment.userVisibleHint = true
            }
            mCurrentPrimaryItem = fragment
        }
    }

    override fun finishUpdate(container: ViewGroup) {
        if (mCurTransaction != null) {
            mCurTransaction!!.commitNowAllowingStateLoss()
            mCurTransaction = null
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as Fragment).view === view
    }

    override fun saveState(): Parcelable? {
        var state: Bundle? = null
        if (mSavedState.size > 0) {
            state = Bundle()
            val fss = arrayOfNulls<Fragment.SavedState>(mSavedState.size)
            mSavedState.toArray(fss)
            state.putParcelableArray("states", fss)
        }
        for (i in mFragments.indices) {
            val f = mFragments[i]
            if (f != null && f.isAdded) {
                if (state == null) {
                    state = Bundle()
                }
                val key = "f$i"
                mFragmentManager.putFragment(state, key, f)
            }
        }
        return state
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        if (state != null) {
            val bundle = state as Bundle
            bundle.classLoader = loader
            val fss = bundle.getParcelableArray("states")
            mSavedState.clear()
            mFragments.clear()
            if (fss != null) {
                for (i in fss.indices) {
                    mSavedState.add(fss[i] as Fragment.SavedState)
                }
            }
            val keys: Iterable<String> = bundle.keySet()
            for (key: String in keys) {
                if (key.startsWith("f")) {
                    val index = key.substring(1).toInt()
                    val f = mFragmentManager.getFragment(bundle, key)
                    if (f != null) {
                        while (mFragments.size <= index) {
                            mFragments.add(null)
                        }
                        f.setMenuVisibility(false)
                        mFragments[index] = f
                    } else {
                        Log.w("TabFragmentAdapter", "Bad fragment at key $key")
                    }
                }
            }
        }
    }

    protected abstract fun createFragment(position: Int): Fragment
}