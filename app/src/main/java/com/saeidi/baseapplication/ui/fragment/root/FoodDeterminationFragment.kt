package com.saeidi.baseapplication.ui.fragment.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.viewmodel.FoodViewModel
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.ui.fragment.fooddeterminator.CalorieSelectorFragment
import com.saeidi.baseapplication.ui.fragment.fooddeterminator.FoodDeterminationResultFragment
import com.saeidi.baseapplication.ui.fragment.fooddeterminator.SelectMaterialFragment
import com.saeidi.baseapplication.ui.fragment.fooddeterminator.TimeSelectorFragment
import com.saeidi.baseapplication.ui.view.CardItem
import com.saeidi.baseapplication.utils.*
import kotlinx.android.synthetic.main.fragment_food_determinator.*
import kotlinx.android.synthetic.main.fragment_food_determinator.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodDeterminationFragment : BaseFragment() {
    private var selectedMaterials = emptyList<String>()
    private var timeRange: Pair<Int, Int>? = null
    private var calorieRange: Pair<Int, Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_food_determinator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.foodSelectMaterialTV.apply {
            typeface = Fonts.bold()
            setOnClickListener { openSelectMaterial() }
        }
        view.foodSelectTimeTV.apply {
            typeface = Fonts.bold()
            setOnClickListener { openSelectTime() }
        }
        view.foodSelectCalorieTV.apply {
            typeface = Fonts.bold()
            setOnClickListener { openSelectCalorie() }
        }

        view.foodSearchBtn.setOnClickListener { computeAndShowResult() }

        view.foodSelectedTimeCI.setCallBack(object : CardItem.EventCallBack {
            override fun onContentClick() {
                openSelectTime()
            }

            override fun onRemoveClick() {
                timeRange = null
                view.foodSelectedTimeCI.gone()
            }

        })

        view.foodSelectedCalorieCI.setCallBack(object : CardItem.EventCallBack {
            override fun onContentClick() {
                openSelectCalorie()
            }

            override fun onRemoveClick() {
                calorieRange = null
                view.foodSelectedCalorieCI.gone()
            }

        })
    }

    private fun openSelectMaterial() {
        SelectMaterialFragment().apply {
            selectedMaterials = this@FoodDeterminationFragment.selectedMaterials
        }.show(childFragmentManager, "SelectMaterialFragment")
    }

    fun onMaterialSelected(materials: List<String>) {
        selectedMaterials = materials
        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(Screen.dp(4f), Screen.dp(4f), Screen.dp(4f), Screen.dp(4f))
        }
        view?.foodMaterialsContainerFB?.apply {
            removeAllViews()
            materials.forEach {
                val cardItem = CardItem(context)
                cardItem.setCallBack(object : CardItem.EventCallBack {
                    override fun onContentClick() {
                        openSelectMaterial()
                    }

                    override fun onRemoveClick() {
                        onMaterialSelected(selectedMaterials.filter { material -> material != it })
                    }
                })
                cardItem.text = it
                addView(cardItem, layoutParams)
            }
            if (childCount > 0) {
                visible()
            } else {
                gone()
            }
        }
    }

    private fun openSelectTime() {
        TimeSelectorFragment().apply { selectedRange = timeRange }
            .show(childFragmentManager, "TimeSelectorFragment")
    }

    fun onTimeSelected(timeRange: Pair<Int, Int>?) {
        this.timeRange = timeRange
        foodSelectedTimeCI.apply {
            if (timeRange == null) {
                gone()
                return@apply
            }
            text = getString(
                R.string.time_formatter,
                LayoutUtil.formatNumber(timeRange.first),
                LayoutUtil.formatNumber(timeRange.second)
            )
            visible()
        }
    }

    private fun openSelectCalorie() {
        CalorieSelectorFragment().apply { selectedRange = calorieRange }
            .show(childFragmentManager, "CalorieSelectorFragment")
    }

    fun onCalorieSelected(calorieRange: Pair<Int, Int>?) {
        this.calorieRange = calorieRange
        foodSelectedCalorieCI.apply {
            if (calorieRange == null) {
                gone()
                return@apply
            }
            text = getString(
                R.string.calorie_formatter,
                LayoutUtil.formatNumber(calorieRange.first),
                LayoutUtil.formatNumber(calorieRange.second)
            )
            visible()
        }
    }

    private fun computeAndShowResult() {
        GlobalScope.launch {
            val allFoods = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(requireActivity().application)
                .create(FoodViewModel::class.java)
                .getAllFoods()
            val selectedFood = allFoods.filter { food ->
                val isMaterialsSatisfied = selectedMaterials.isEmpty() ||
                        food.materials.map { it.name }.intersect(selectedMaterials).isNotEmpty()
                val isTimeSatisfied = timeRange == null ||
                        (timeRange!!.first <= food.duration && food.duration <= timeRange!!.second)
                val isCalorieSatisfied = calorieRange == null ||
                        (calorieRange!!.first <= food.calorie && food.calorie <= calorieRange!!.second)
                return@filter isMaterialsSatisfied && isTimeSatisfied && isCalorieSatisfied
            }
            FoodDeterminationResultFragment().apply {
                result = selectedFood
            }.show(childFragmentManager, "FoodDeterminationResultFragment")
        }
    }
}