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
import kotlinx.android.synthetic.main.fragment_select_time.*
import kotlinx.android.synthetic.main.fragment_select_time.view.*

class TimeSelectorFragment : FullBottomSheetFragment() {
    override var bottomSheetId = R.id.selectTimeBottomSheet
    override var sheetCancelId = R.id.selectTimeSheetCancel
    override var toolbarTopId = R.id.selectTimeToolbarTop
    override var toolbarBottomId = R.id.selectTimeToolbar

    var selectedRange: Pair<Int, Int>? = Pair(1, 180)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.selectTimeTopCancelIV.setOnClickListener { forceHide() }
        view.selectTimeCancelIV.setOnClickListener { forceHide() }
        view.selectTimeTopDoneIV.setOnClickListener { next() }
        view.selectTimeDoneIV.setOnClickListener { next() }

        updateSlider()
        view.selectTimeSliderRS.apply {
            setLabelFormatter {
                return@setLabelFormatter LayoutUtil.formatNumber("${it.toInt()} ${getString(R.string.minute)}")
            }
            addOnChangeListener { slider, _, fromUser ->
                if (fromUser) {
                    selectedRange = Pair(slider.values[0].toInt(), slider.values[1].toInt())
                    updateEditTexts()
                }
            }
        }
        updateEditTexts()

        view.selectTimeFromET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                var from = s.toString().toIntOrNull() ?: 1
                if (from < 1) {
                    from = 1
                    view.selectTimeFromET.setText(from.toString())
                }
                selectedRange = Pair(from, selectedRange?.second ?: 180)
                updateSlider()
            }

        })
        view.selectTimeToET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                var to = s.toString().toIntOrNull() ?: 180
                if (to > 180) {
                    to = 180
                    view.selectTimeToET.setText(to.toString())
                }
                selectedRange = Pair(selectedRange?.first ?: 1, to)
                updateSlider()
            }
        })
    }

    private fun updateEditTexts() {
        selectTimeFromET.setText(selectedRange?.first?.toString() ?: "")
        selectTimeToET.setText(selectedRange?.second?.toString() ?: "")
    }

    private fun updateSlider() {
        selectTimeSliderRS.values =
            selectedRange?.let { listOf(it.first.toFloat(), it.second.toFloat()) }
                ?: run { listOf(1f, 180f) }
    }

    private fun next() {
        (parentFragment as FoodDeterminationFragment).onTimeSelected(selectedRange)
        forceHide()
    }
}