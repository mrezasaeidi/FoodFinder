package com.saeidi.baseapplication.storage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.saeidi.baseapplication.storage.repository.local.LocalDatabase
import com.saeidi.baseapplication.storage.repository.local.entity.CategoryModel

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryDao = LocalDatabase.getDatabase(application).categoryDao()

    fun getAllCategoryLive(): LiveData<List<CategoryModel>> {
        return categoryDao.getAllCategoryLive()
    }

    suspend fun getCategory(categoryId: Int): CategoryModel? {
        return categoryDao.getCategory(categoryId)
    }
}