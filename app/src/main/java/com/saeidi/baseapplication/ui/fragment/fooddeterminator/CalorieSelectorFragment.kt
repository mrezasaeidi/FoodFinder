package com.saeidi.baseapplication.ui.fragment.fooddeterminator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.ui.fragment.base.FullBottomSheetFragment
import com.saeidi.baseapplication.ui.fragment.root.FoodDeterminationFragment
import com.saeidi.baseapplication.utils.LayoutUtil
import kotlinx.android.synthetic.main.fragment_select_calorie.*
import kotlinx.android.synthetic.main.fragment_select_calorie.view.*

class CalorieSelectorFragment : FullBottomSheetFragment() {
    override var bottomSheetId = R.id.selectCalorieBottomSheet
    override var sheetCancelId = R.id.selectCalorieSheetCancel
    override var toolbarTopId = R.id.selectCalorieToolbarTop
    override var toolbarBottomId = R.id.selectCalorieToolbar

    var selectedRange: Pair<Int, Int>? = Pair(1, 15000)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_calorie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.selectCalorieTopCancelIV.setOnClickListener { forceHide() }
        view.selectCalorieCancelIV.setOnClickListener { forceHide() }
        view.selectCalorieTopDoneIV.setOnClickListener { next() }
        view.selectCalorieDoneIV.setOnClickListener { next() }

        updateSlider()
        view.selectCalorieSliderRS.apply {
            setLabelFormatter {
                return@setLabelFormatter LayoutUtil.formatNumber("${it.toInt()} ${getString(R.string.calorie)}")
            }
            addOnChangeListener { slider, _, fromUser ->
                if (fromUser) {
                    selectedRange = Pair(slider.values[0].toInt(), slider.values[1].toInt())
                    updateEditTexts()
                }
            }
        }
        updateEditTexts()

        view.selectCalorieFromET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                var from = s.toString().toIntOrNull() ?: 1
                if (from < 1) {
                    from = 1
                    view.selectCalorieFromET.setText(from.toString())
                }
                selectedRange = Pair(from, selectedRange?.second ?: 15000)
                updateSlider()
            }

        })
        view.selectCalorieToET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                var to = s.toString().toIntOrNull() ?: 15000
                if (to > 15000) {
                    to = 15000
                    view.selectCalorieToET.setText(to.toString())
                }
                selectedRange = Pair(selectedRange?.first ?: 1, to)
                updateSlider()
            }
        })
    }

    private fun updateEditTexts() {
        selectCalorieFromET.setText(selectedRange?.first?.toString() ?: "")
        selectCalorieToET.setText(selectedRange?.second?.toString() ?: "")
    }

    private fun updateSlider() {
        selectCalorieSliderRS.values =
            selectedRange?.let { listOf(it.first.toFloat(), it.second.toFloat()) }
                ?: run { listOf(1f, 15000f) }
    }

    private fun next() {
        (parentFragment as FoodDeterminationFragment).onCalorieSelected(selectedRange)
        forceHide()
    }
}