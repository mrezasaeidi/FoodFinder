package com.saeidi.baseapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.RecipeStep
import com.saeidi.baseapplication.utils.*
import kotlinx.android.synthetic.main.recipes_item.view.*

class RecipesAdapter(private val fragment: Fragment, private val recipes: List<RecipeStep>) :
    RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private fun getItem(position: Int) = recipes.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewUtils.inflate(R.layout.recipes_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = recipes.size

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(recipe: RecipeStep) {
            itemView.apply {
                recipeStepTV.apply {
                    typeface = Fonts.bold()
                    text = LayoutUtil.formatNumber(recipe.step)
                }
                recipeTextTV.text = recipe.text
                if (recipe.photoUrl.isNullOrEmpty()) {
                    recipeImageIV.gone()
                } else {
                    Glide.with(fragment).load(recipe.photoUrl)
                        .transform(RoundedCorners(Screen.dp(16f)))
                        .placeholder(R.drawable.ic_chef)
                        .error(R.drawable.ic_chef)
                        .into(recipeImageIV)
                    recipeImageIV.visible()
                }
            }
        }
    }
}
