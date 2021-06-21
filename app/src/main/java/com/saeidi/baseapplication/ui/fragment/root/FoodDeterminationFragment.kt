package com.saeidi.baseapplication.ui.fragment.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayout
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.ui.fragment.fooddeterminator.SelectMaterialFragment
import com.saeidi.baseapplication.ui.view.CardItem
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Screen
import com.saeidi.baseapplication.utils.gone
import com.saeidi.baseapplication.utils.visible
import kotlinx.android.synthetic.main.fragment_food_determinator.view.*

class FoodDeterminationFragment : BaseFragment() {
    private var selectedMaterials = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_determinator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.foodSelectMaterialTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {
                SelectMaterialFragment().apply {
                    selectedMaterials = this@FoodDeterminationFragment.selectedMaterials
                }.show(childFragmentManager, "SelectMaterialFragment")
            }
        }
        view.foodSelectTimeTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {

            }
        }
        view.foodSelectCalorieTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {

            }
        }

        view.foodSearchBtn.setOnClickListener {

        }
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
                    override fun onContentClick() = Unit

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
}