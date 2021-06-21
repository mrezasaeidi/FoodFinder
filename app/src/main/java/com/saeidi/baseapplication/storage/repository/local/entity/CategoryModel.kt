package com.saeidi.baseapplication.storage.repository.local.entity

import androidx.room.Entity

@Entity(tableName = "category")
data class CategoryModel(val id: Int, val name: String, val photoUrl: String)