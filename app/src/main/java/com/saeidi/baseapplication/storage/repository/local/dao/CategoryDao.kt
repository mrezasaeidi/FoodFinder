package com.saeidi.baseapplication.storage.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saeidi.baseapplication.storage.repository.local.entity.CategoryModel

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(categoryModel: CategoryModel)

    @Delete
    fun deleteCategory(categoryModel: CategoryModel)

    @Query("DELETE FROM category")
    fun deleteCategories()

    @Query("SELECT * FROM category WHERE id=:id")
    suspend fun getCategory(id: Int): CategoryModel?

    @Query("SELECT * FROM category")
    fun getAllCategoryLive(): LiveData<List<CategoryModel>>
}