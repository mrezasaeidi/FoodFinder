package com.saeidi.baseapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.saeidi.baseapplication.utils.Fonts

class MaterialsAdapter(
    private val allMaterials: List<String>,
    checkedItems: ArrayList<String>,
    private val onSelectedChanged: (ArrayList<String>) -> Unit
) : RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

    var query = ""
        set(value) {
            field = value
            materials = allMaterials.filter { it.contains(query) }
        }

    private var materials = allMaterials.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var checkedItems = checkedItems
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun getItem(position: Int) = materials.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AppCompatCheckBox(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = materials.size

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(material: String) {
            (itemView as CheckBox).apply {
                text = material
                typeface = Fonts.light()
                setOnCheckedChangeListener(null)
                isChecked = checkedItems.contains(material)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        if (!checkedItems.contains(material)) {
                            checkedItems.add(material)
                            onSelectedChanged.invoke(checkedItems)
                        }
                    } else {
                        if (checkedItems.removeAll { it == material }) {
                            onSelectedChanged.invoke(checkedItems)
                        }
                    }
                }
            }
        }
    }
}