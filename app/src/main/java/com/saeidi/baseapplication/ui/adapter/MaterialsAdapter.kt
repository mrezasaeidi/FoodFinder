package com.saeidi.baseapplication.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.repository.local.entity.Material
import com.saeidi.baseapplication.storage.repository.local.entity.Unit
import com.saeidi.baseapplication.utils.LayoutUtil
import com.saeidi.baseapplication.utils.ViewUtils
import kotlinx.android.synthetic.main.material_item.view.*

class MaterialsAdapter(private val materials: List<Material>) :
    RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

    private fun getItem(position: Int) = materials.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewUtils.inflate(R.layout.material_item, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = materials.size

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(material: Material) {
            itemView.apply {
                materialItemNameTV.text = material.name
                materialItemAmountTV.text =
                    formatMaterialAmount(context, material.amount, material.unit)
            }
        }

        private fun formatMaterialAmount(context: Context, amount: Float?, unit: Unit?): String {
            if (amount == null) {
                return context.getString(R.string.necessary_amount)
            }
            return when (unit) {
                Unit.GRAM -> context.getString(
                    R.string.unit_gram,
                    LayoutUtil.formatNumber(amount.toString())
                )
                Unit.LITRE -> context.getString(
                    R.string.unit_litre,
                    LayoutUtil.formatNumber(amount.toString())
                )
                Unit.SPOON -> context.getString(
                    R.string.unit_spoon,
                    LayoutUtil.formatNumber(amount.toString())
                )
                Unit.PIECE -> context.getString(
                    R.string.unit_piece,
                    LayoutUtil.formatNumber(amount.toString())
                )
                Unit.MODULE -> context.getString(
                    R.string.unit_module,
                    LayoutUtil.formatNumber(amount.toString())
                )
                null -> context.getString(R.string.necessary_amount)
            }
        }
    }
}