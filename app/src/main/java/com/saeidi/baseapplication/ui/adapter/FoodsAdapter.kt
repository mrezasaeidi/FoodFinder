package com.saeidi.baseapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.storage.viewmodel.UserViewModel
import com.saeidi.baseapplication.utils.*
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodsAdapter(
    private val fragment: Fragment,
    private val categoryViewModel: CategoryViewModel,
    private val userViewModel: UserViewModel,
    private val onItemClicked: (FoodModel) -> Unit
) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {

    var items = emptyList<FoodModel>()
    private val radius = Screen.dp(16f)

    private fun getItem(position: Int) = items.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewUtils.inflate(R.layout.food_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(foodModel: FoodModel) {
            itemView.apply {
                setOnClickListener { onItemClicked.invoke(foodModel) }
                if (foodModel.photosUrl.isEmpty()) {
                    foodImageIV.setImageResource(R.drawable.ic_chef)
                } else {
                    Glide.with(fragment).load(foodModel.photosUrl[0])
                        .transform(RoundedCorners(radius))
                        .placeholder(R.drawable.ic_chef)
                        .error(R.drawable.ic_chef)
                        .into(foodImageIV)
                }
                foodCategoryTV.apply {
                    GlobalScope.launch(Dispatchers.Main) {
                        categoryViewModel.getCategory(foodModel.categoryId)?.let {
                            text = if (LayoutUtil.isFa()) {
                                it.nameFa
                            } else {
                                it.nameEn
                            }
                            visible()
                        } ?: run {
                            gone()
                        }
                    }
                }
                foodNameTV.text = foodModel.name
                foodSenderNameTV.apply {
                    GlobalScope.launch(Dispatchers.Main) {
                        userViewModel.getUser(foodModel.senderUserId)?.let {
                            text = it.name
                            visible()
                        } ?: run {
                            gone()
                        }
                    }
                }
                foodCommentTV.text = LayoutUtil.formatNumber(foodModel.commentCount)
                foodLikeTV.text = LayoutUtil.formatNumber(foodModel.likesCount)
            }
        }
    }
}