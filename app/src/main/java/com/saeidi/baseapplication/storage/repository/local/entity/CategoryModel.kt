package com.saeidi.baseapplication.storage.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "category")
data class CategoryModel(
    @NotNull
    @PrimaryKey
    val id: Int,
    val nameFa: String,
    val nameEn: String,
    val photoUrl: String?
)