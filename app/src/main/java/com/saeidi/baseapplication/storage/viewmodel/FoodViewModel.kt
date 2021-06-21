package com.saeidi.baseapplication.storage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.saeidi.baseapplication.storage.repository.local.LocalDatabase
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val foodDao = LocalDatabase.getDatabase(application).foodDao()

    fun getAllFoodsLive(): LiveData<List<FoodModel>> {
        return foodDao.getAllFoodsLive()
    }
}