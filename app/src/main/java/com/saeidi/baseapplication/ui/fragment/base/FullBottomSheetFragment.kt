package com.saeidi.baseapplication.ui.fragment.base

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saeidi.baseapplication.utils.Screen
import com.saeidi.baseapplication.utils.ViewUtils.goneView
import com.saeidi.baseapplication.utils.ViewUtils.showView
import com.saeidi.baseapplication.utils.gone
import com.saeidi.baseapplication.utils.invisible
import com.saeidi.baseapplication.utils.visible
import com.saeidi.baseapplication.utils.Runtime

abstract class FullBottomSheetFragment : DialogFragment() {

    abstract var bottomSheetId: Int
    abstract var sheetCancelId: Int
    abstract var toolbarTopId: Int
    abstract var toolbarBottomId: Int

    private var isBottomSheetFullScreen: Boolean = false
    private var isBottomSheetActive = false
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var isLoaded = false
    private var sheetCancel: View? = null
    private var toolbarTop: View? = null
    private var toolbarBottom: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUi()
        isBottomSheetActive = true
        bottomSheetBehavior!!.peekHeight = Screen.getHeight() / 2
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        showView(sheetCancel, true)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!isBottomSheetFullScreen) {
            forceHide()
        }
    }

    private fun prepareUi() {
        if (isLoaded) {
            return
        }
        isLoaded = true
        sheetCancel = view?.findViewById(sheetCancelId)
        toolbarTop = view?.findViewById(toolbarTopId)
        toolbarBottom = view?.findViewById(toolbarBottomId)
        sheetCancel?.bringToFront()

        sheetCancel?.setOnClickListener { hide() }

        val bottomSheet = view?.findViewById<View>(bottomSheetId)
        bottomSheet?.visible()
        bottomSheet?.bringToFront()
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
        bottomSheetBehavior?.isHideable = true
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior?.isHideable = true
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                    }
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior?.isHideable = false
                    toolbarTop?.visible()
                    toolbarTop?.bringToFront()
                    toolbarBottom?.invisible()
                    isBottomSheetFullScreen = true
                } else {
                    toolbarBottom?.visible()
                    toolbarTop?.gone()
                    isBottomSheetFullScreen = false
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        isBottomSheetFullScreen = false
    }

    open fun forceHide() {
        bottomSheetBehavior?.isHideable = true
        hide()
    }

    open fun hide() {
        if (bottomSheetBehavior == null || bottomSheetBehavior?.peekHeight == 0) {
            dismiss()
            return
        }
        if (bottomSheetBehavior!!.isHideable) {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
            bottomSheetBehavior!!.peekHeight = 0
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        goneView(sheetCancel, true)
        isBottomSheetActive = false

        Runtime.postDelayedToMainThread({
            dismiss()
        }, 200)
    }
}