package com.saeidi.baseapplication.ui.fragment.root

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.storage.viewmodel.CategoryViewModel
import com.saeidi.baseapplication.storage.viewmodel.FoodViewModel
import com.saeidi.baseapplication.storage.viewmodel.UserViewModel
import com.saeidi.baseapplication.ui.adapter.FoodsAdapter
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.utils.Intents
import com.saeidi.baseapplication.utils.ViewUtils
import com.saeidi.baseapplication.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment() {
    private lateinit var foodVM: FoodViewModel
    private lateinit var foodsAdapter: FoodsAdapter
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(requireActivity().application)

        val userVM = viewModelFactory.create(UserViewModel::class.java)
        val categoryVM = viewModelFactory.create(CategoryViewModel::class.java)
        foodVM = viewModelFactory.create(FoodViewModel::class.java)

        foodsAdapter = FoodsAdapter(this, categoryVM, userVM) {
            startActivity(Intents.openFood(requireActivity(), it.id))
        }
        view.searchFoodResultCollectionRV.adapter = foodsAdapter

        searchFoodSearchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable) {
                searchQuery = s.toString()
                initSearch()
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (view?.searchFoodResultCollectionRV?.layoutManager as GridLayoutManager).spanCount =
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                3
            } else {
                2
            }
    }

    private fun initSearch() {
        if (searchQuery.isEmpty()) {
            foodsAdapter.items = emptyList()
            checkEmpty()
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            val result = foodVM.getFoodsByName(searchQuery)
            GlobalScope.launch(Dispatchers.Main) {
                foodsAdapter.items = result
                checkEmpty(true)
            }
        }

    }

    private fun checkEmpty(animate: Boolean = false) {
        if (foodsAdapter.itemCount == 0) {
            searchFragSearchHintTV.apply {
                text = if (searchQuery.isEmpty()) {
                    getString(R.string.search_hint)
                } else {
                    getString(R.string.search_empty_result)
                }
                ViewUtils.zoomInView(this)
            }
            ViewUtils.zoomOutView(searchFoodResultCollectionRV)
        } else {
            ViewUtils.zoomOutView(searchFragSearchHintTV)
            searchFoodResultCollectionRV.apply {
                visible()
                if (animate) {
                    scheduleLayoutAnimation()
                }
            }
        }
    }
}