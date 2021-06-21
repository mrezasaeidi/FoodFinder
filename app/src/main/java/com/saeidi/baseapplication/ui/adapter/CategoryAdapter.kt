package com.saeidi.baseapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.CategoryModel
import com.saeidi.baseapplication.utils.Fonts
import com.saeidi.baseapplication.utils.LayoutUtil
import com.saeidi.baseapplication.utils.Screen
import com.saeidi.baseapplication.utils.ViewUtils
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(
    private val fragment: Fragment,
    private val onItemClicked: (CategoryModel) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val radius = Screen.dp(16f)

    var categories = ArrayList<CategoryModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun getItem(position: Int) = categories.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewUtils.inflate(R.layout.category_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(categoryModel: CategoryModel) {
            itemView.apply {
                setOnClickListener { onItemClicked.invoke(categoryModel) }
                categoryImageIV.apply {
                    if (categoryModel.photoUrl.isNullOrEmpty()) {
                        setImageResource(R.drawable.ic_chef)
                    } else {
                        Glide.with(fragment).load(categoryModel.photoUrl)
                            .transform(RoundedCorners(radius))
                            .placeholder(R.drawable.ic_chef)
                            .error(R.drawable.ic_chef)
                            .into(this)
                    }
                }
                categoryNameTV.apply {
                    typeface = Fonts.bold()
                    text = if (LayoutUtil.isFa()) {
                        categoryModel.nameFa
                    } else {
                        categoryModel.nameEn
                    }
                }
            }
        }
    }
}