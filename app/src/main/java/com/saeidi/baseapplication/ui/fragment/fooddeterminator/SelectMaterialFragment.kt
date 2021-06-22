package com.saeidi.baseapplication.ui.fragment.fooddeterminator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.viewmodel.FoodViewModel
import com.saeidi.baseapplication.ui.adapter.SelectMaterialsAdapter
import com.saeidi.baseapplication.ui.fragment.base.FullBottomSheetFragment
import com.saeidi.baseapplication.ui.fragment.root.FoodDeterminationFragment
import com.saeidi.baseapplication.ui.view.CardItem
import com.saeidi.baseapplication.utils.Screen
import com.saeidi.baseapplication.utils.gone
import com.saeidi.baseapplication.utils.visible
import kotlinx.android.synthetic.main.fragment_select_material.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectMaterialFragment : FullBottomSheetFragment() {
    override var bottomSheetId = R.id.selectFoodBottomSheet
    override var sheetCancelId = R.id.selectFoodSheetCancel
    override var toolbarTopId = R.id.selectFoodToolbarTop
    override var toolbarBottomId = R.id.selectFoodToolbar

    private lateinit var selectMaterialsAdapter: SelectMaterialsAdapter
    var selectedMaterials = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_material, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.selectFoodTopCancelIV.setOnClickListener { forceHide() }
        view.selectFoodCancelIV.setOnClickListener { forceHide() }
        view.selectFoodTopDoneIV.setOnClickListener { next() }
        view.selectFoodDoneIV.setOnClickListener { next() }
        view.selectFoodSearchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                selectMaterialsAdapter.query = s.toString()
            }
        })

        selectMaterialsAdapter = SelectMaterialsAdapter(
            ArrayList(selectedMaterials)
        ) {
            updateSelectedMaterials(it)
        }
        view.selectFoodMaterialListRV.adapter = selectMaterialsAdapter
        updateSelectedMaterials(selectedMaterials)
        GlobalScope.launch {
            val allMaterials = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(requireActivity().application)
                .create(FoodViewModel::class.java)
                .getAllFoods()
                .map { food -> food.materials.map { it.name } }
                .flatten()
                .toSet()
                .toList()
            GlobalScope.launch(Dispatchers.Main) {
                selectMaterialsAdapter.allMaterials = allMaterials
            }
        }
    }

    private fun updateSelectedMaterials(selected: List<String>) {
        selectedMaterials = selected
        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(Screen.dp(4f), Screen.dp(4f), Screen.dp(4f), Screen.dp(4f))
        }
        view?.selectFoodSelectedFB?.apply {
            removeAllViews()
            selectedMaterials.forEach {
                val cardItem = CardItem(context)
                cardItem.setCallBack(object : CardItem.EventCallBack {
                    override fun onContentClick() = Unit

                    override fun onRemoveClick() {
                        updateSelectedMaterials(selectedMaterials.filter { material -> material != it })
                        selectMaterialsAdapter.checkedItems = ArrayList(selectedMaterials)
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

    private fun next() {
        (parentFragment as FoodDeterminationFragment).onMaterialSelected(selectedMaterials)
        forceHide()
    }
}