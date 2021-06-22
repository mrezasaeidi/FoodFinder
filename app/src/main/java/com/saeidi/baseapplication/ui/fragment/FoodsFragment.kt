package com.saeidi.baseapplication.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.storage.viewmodel.FoodViewModel
import com.saeidi.baseapplication.storage.viewmodel.UserViewModel
import com.saeidi.baseapplication.ui.adapter.FoodsAdapter
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.utils.Intents
import com.saeidi.baseapplication.utils.Intents.PARAM_1
import com.saeidi.baseapplication.utils.LayoutUtil
import com.saeidi.baseapplication.utils.getColorCompat
import kotlinx.android.synthetic.main.fragment_foods.*
import kotlinx.android.synthetic.main.fragment_foods.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodsFragment : BaseFragment() {

    init {
        setTitle(R.string.recipes)
    }

    private var categoryId = 0
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var userVM: UserViewModel
    private lateinit var foodVM: FoodViewModel
    private var selectedSort = SortType.NEWEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = requireActivity().intent.getIntExtra(PARAM_1, 0)
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).let {
            categoryVM = it.create(CategoryViewModel::class.java)
            userVM = it.create(UserViewModel::class.java)
            foodVM = it.create(FoodViewModel::class.java)
        }
        if (categoryId != 0) {
            GlobalScope.launch(Dispatchers.Main) {
                categoryVM.getCategory(categoryId)?.let {
                    setTitle(if (LayoutUtil.isFa()) it.nameFa else it.nameEn)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_foods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodsAdapters = FoodsAdapter(this, categoryVM, userVM) {
            startActivity(Intents.openFood(requireActivity(), it.id))
        }
        view.foodsCollectionRV.adapter = foodsAdapters
        val liveCollection = if (categoryId == 0) {
            foodVM.getAllFoodsLive()
        } else {
            foodVM.getCategoryFoodsLive(categoryId)
        }
        liveCollection.observe(viewLifecycleOwner) {
            applyList(foodsAdapters, it)
        }
        view.foodSortNewTV.setOnClickListener {
            updateSort(SortType.NEWEST)
            applyList(foodsAdapters, foodsAdapters.items)
        }
        view.foodSortBestWeekTV.setOnClickListener {
            updateSort(SortType.WEEK)
            applyList(foodsAdapters, foodsAdapters.items)
        }
        view.foodSortBestUserTV.setOnClickListener {
            updateSort(SortType.USER)
            applyList(foodsAdapters, foodsAdapters.items)
        }
        updateSort(selectedSort)
    }

    fun applyList(adapter: FoodsAdapter, foods: List<FoodModel>) {
        adapter.items = when (selectedSort) {
            SortType.NEWEST -> foods.sortedByDescending { it.date }
            SortType.WEEK -> foods.sortedByDescending { it.commentCount }
            SortType.USER -> foods.sortedByDescending { it.likesCount }
        }
        foodsCollectionRV.scheduleLayoutAnimation()
    }

    private fun updateSort(selectedSort: SortType) {
        this.selectedSort = selectedSort
        updateSortButton(foodSortNewTV, selectedSort == SortType.NEWEST)
        updateSortButton(foodSortBestWeekTV, selectedSort == SortType.WEEK)
        updateSortButton(foodSortBestUserTV, selectedSort == SortType.USER)
    }

    private fun updateSortButton(button: AppCompatTextView, isSelected: Boolean) {
        button.apply {
            background = if (isSelected) {
                setTextColor(Color.WHITE)
                AppCompatResources.getDrawable(context, R.drawable.button_bg_secondary)
            } else {
                setTextColor(context.getColorCompat(R.color.secondary_color))
                null
            }
        }
    }

    enum class SortType {
        NEWEST, WEEK, USER
    }
}