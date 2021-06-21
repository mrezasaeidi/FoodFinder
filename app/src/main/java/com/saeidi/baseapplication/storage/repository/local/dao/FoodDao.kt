package com.saeidi.baseapplication.storage.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saeidi.baseapplication.storage.repository.local.entity.FoodModel

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(foodModel: FoodModel)

    @Delete
    fun deleteFood(foodModel: FoodModel)

    @Query("DELETE FROM food")
    fun deleteFoods()

    @Query("SELECT * FROM food WHERE id=:id")
    suspend fun getFood(id: Int): FoodModel?

    @Query("SELECT * FROM food")
    fun getAllFoodsLive(): LiveData<List<FoodModel>>

    @Query("SELECT * FROM food WHERE categoryId=:categoryId")
    fun getCategoryFoodsLive(categoryId: Int): LiveData<List<FoodModel>>

    @Query("SELECT * FROM food WHERE name LIKE '%' || :name || '%'")
    fun getFoodsByNameLive(name: String): LiveData<List<FoodModel>>

    @Query("SELECT * FROM food WHERE name LIKE '%' || :name || '%'")
    suspend fun getFoodsByName(name: String): List<FoodModel>
}