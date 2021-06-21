package com.saeidi.baseapplication.storage.repository.local

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saeidi.baseapplication.storage.repository.local.dao.CategoryDao
import com.saeidi.baseapplication.storage.repository.local.dao.FoodDao
import com.saeidi.baseapplication.storage.repository.local.dao.UserDao
import com.saeidi.baseapplication.storage.repository.local.entity.*

@Database(
    entities = [CategoryModel::class, FoodModel::class, UserModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FoodTypeConvertor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun foodDao(): FoodDao
    abstract fun userDao(): UserDao

    fun deleteDatabase() {
        categoryDao().deleteCategories()
        foodDao().deleteFoods()
        userDao().deleteUsers()
    }

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, LocalDatabase::class.java, "local_database")
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

object FoodTypeConvertor {
    @TypeConverter
    @JvmStatic
    fun photoArrayToString(photoArray: List<String>): String? = Gson().toJson(photoArray)

    @TypeConverter
    @JvmStatic
    fun photoStringToArray(photoJson: String?): List<String> {
        return if (photoJson == null || photoJson == "null") {
            emptyList()
        } else {
            Gson().fromJson(photoJson, object : TypeToken<List<String>>() {}.type)
        }
    }

    @TypeConverter
    @JvmStatic
    fun recipeArrayToString(recipeSteps: List<RecipeStep>): String? = Gson().toJson(recipeSteps)

    @TypeConverter
    @JvmStatic
    fun recipesStringToArray(recipeStepsJson: String?): List<RecipeStep> {
        return if (recipeStepsJson == null || recipeStepsJson == "null") {
            emptyList()
        } else {
            Gson().fromJson(recipeStepsJson, object : TypeToken<List<RecipeStep>>() {}.type)
        }
    }

    @TypeConverter
    @JvmStatic
    fun materialArrayToString(materials: List<Material>): String? = Gson().toJson(materials)

    @TypeConverter
    @JvmStatic
    fun materialsStringToArray(materialsJson: String?): List<Material> {
        return if (materialsJson == null || materialsJson == "null") {
            emptyList()
        } else {
            Gson().fromJson(materialsJson, object : TypeToken<List<Material>>() {}.type)
        }
    }
}