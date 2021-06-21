package com.saeidi.baseapplication.storage.repository.local.entity

import androidx.room.Entity

@Entity(tableName = "food")
data class FoodModel(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val photosUrl: List<String>,
    val recipe: List<RecipeStep>,
    val materials: List<Material>,
    val duration: Int,
    val date: Long,
    val senderUserId: Int,
    val likesCount: Int,
    val commentCount: Int
)