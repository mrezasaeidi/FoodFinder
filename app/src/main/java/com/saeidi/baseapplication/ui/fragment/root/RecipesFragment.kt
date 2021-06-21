package com.saeidi.baseapplication.ui.fragment.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.ui.adapter.CategoryAdapter
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.utils.Intents
import kotlinx.android.synthetic.main.fragment_recipes.view.*

class RecipesFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAdapter = CategoryAdapter(this) {
            startActivity(Intents.openCategory(requireActivity(), it.id))
        }
        view.categoryCollectionRV.adapter = categoryAdapter
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(CategoryViewModel::class.java).getAllCategoryLive()
            .observe(viewLifecycleOwner) {
                categoryAdapter.categories = it
            }
    }
}