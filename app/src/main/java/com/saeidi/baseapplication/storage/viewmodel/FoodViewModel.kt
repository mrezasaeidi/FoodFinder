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

    suspend fun getAllFoods(): List<FoodModel> {
        return foodDao.getAllFoods()
    }

    fun getCategoryFoodsLive(categoryId: Int): LiveData<List<FoodModel>> {
        return foodDao.getCategoryFoodsLive(categoryId)
    }

    fun getFoodsByNameLive(name: String): LiveData<List<FoodModel>> {
        return foodDao.getFoodsByNameLive(name)
    }

    suspend fun getFoodsByName(name: String): List<FoodModel> {
        return foodDao.getFoodsByName(name)
    }

    suspend fun getFood(id: Int): FoodModel? {
        return foodDao.getFood(id)
    }
}