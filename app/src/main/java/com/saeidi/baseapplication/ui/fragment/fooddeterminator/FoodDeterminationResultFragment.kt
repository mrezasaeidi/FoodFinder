package com.saeidi.baseapplication.ui.fragment.fooddeterminator

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.storage.viewmodel.UserViewModel
import com.saeidi.baseapplication.ui.adapter.FoodsAdapter
import com.saeidi.baseapplication.ui.fragment.base.FullBottomSheetFragment
import com.saeidi.baseapplication.utils.Intents
import com.saeidi.baseapplication.utils.gone
import com.saeidi.baseapplication.utils.visible
import kotlinx.android.synthetic.main.fragment_food_determination_result.view.*

class FoodDeterminationResultFragment : FullBottomSheetFragment() {
    override var bottomSheetId = R.id.foodResultBottomSheet
    override var sheetCancelId = R.id.foodResultSheetCancel
    override var toolbarTopId = R.id.foodResultToolbarTop
    override var toolbarBottomId = R.id.foodResultToolbar

    private lateinit var foodsAdapter: FoodsAdapter
    var result = emptyList<FoodModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_food_determination_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.foodResultTopCancelIV.setOnClickListener { forceHide() }
        view.foodResultCancelIV.setOnClickListener { forceHide() }
        val (categoryVM, userVM) = ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application
        ).let {
            Pair(
                it.create(CategoryViewModel::class.java),
                it.create(UserViewModel::class.java)
            )
        }
        foodsAdapter = FoodsAdapter(this, categoryVM, userVM) {
            startActivity(Intents.openFood(requireActivity(), it.id))
        }
        view.foodResultListRV.adapter = foodsAdapter
        foodsAdapter.items = result
        if (result.isEmpty()) {
            view.foodResultEmptyTV.visible()
            view.foodResultListRV.gone()
        } else {
            view.foodResultEmptyTV.gone()
            view.foodResultListRV.visible()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (view?.foodResultListRV?.layoutManager as GridLayoutManager).spanCount =
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
    }
}