package com.saeidi.baseapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.CategoryModel
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.storage.viewmodel.FoodViewModel
import com.saeidi.baseapplication.ui.adapter.MaterialsAdapter
import com.saeidi.baseapplication.ui.adapter.RecipesAdapter
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.Intents.PARAM_1
import com.saeidi.baseapplication.utils.LayoutUtil
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.fragment_food.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodFragment : BaseFragment() {

    init {
        setTitle("")
    }

    private lateinit var foodModel: FoodModel
    private lateinit var foodCategory: CategoryModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodId = requireActivity().intent.getIntExtra(PARAM_1, 0)
        if (foodId == 0) {
            activity?.finish()
            return
        }
        GlobalScope.launch {
            val viewModelFactory = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(requireActivity().application)
            val foodViewModel = viewModelFactory.create(FoodViewModel::class.java)
            foodModel = foodViewModel.getFood(foodId) ?: run {
                activity?.finish()
                return@launch
            }
            val categoryViewModel = viewModelFactory.create(CategoryViewModel::class.java)
            foodCategory = categoryViewModel.getCategory(foodModel.categoryId) ?: run {
                activity?.finish()
                return@launch
            }
            GlobalScope.launch(Dispatchers.Main) {
                setTitle(foodModel.name)
                if (foodModel.photosUrl.isEmpty()) {
                    view.foodFragImageIV.setImageResource(R.drawable.ic_chef)
                } else {
                    Glide.with(this@FoodFragment).load(foodModel.photosUrl[0])
                        .placeholder(R.drawable.ic_chef)
                        .error(R.drawable.ic_chef)
                        .into(view.foodFragImageIV)
                }
                view.foodFragNameTV.apply {
                    typeface = Fonts.bold()
                    text = foodModel.name
                }
                view.foodFragCategoryTV.text =
                    "#${if (LayoutUtil.isFa()) foodCategory.nameFa else foodCategory.nameEn}"

                view.foodFragCommentTV.text = LayoutUtil.formatNumber(foodModel.commentCount)
                view.foodFragLikeTV.text = LayoutUtil.formatNumber(foodModel.likesCount)
                view.foodFragTimeTV.text = LayoutUtil.formatNumber(foodModel.duration)
                view.foodFragCountTV.text = LayoutUtil.formatNumber(foodModel.memberCount)
                view.foodFragMaterialRV.adapter = MaterialsAdapter(foodModel.materials)
                view.foodFragRecipesRV.adapter = RecipesAdapter(this@FoodFragment, foodModel.recipe)
            }
        }
    }
}