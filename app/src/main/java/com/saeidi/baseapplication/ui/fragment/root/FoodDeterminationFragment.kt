package com.saeidi.baseapplication.ui.fragment.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.ui.fragment.base.BaseFragment
import com.saeidi.baseapplication.utils.Fonts
import kotlinx.android.synthetic.main.fragment_food_determinator.view.*

class FoodDeterminationFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_determinator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.foodSelectMaterialTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {

            }
        }
        view.foodSelectTimeTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {

            }
        }
        view.foodSelectCalorieTV.apply {
            typeface = Fonts.bold()
            setOnClickListener {

            }
        }

        view.foodSearchBtn.setOnClickListener {

        }

    }
}