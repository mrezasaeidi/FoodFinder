package com.saeidi.baseapplication.storage.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user")
data class UserModel(
    @NotNull
    @PrimaryKey
    val id: Int,
    val name: String,
    val avatarUrl: String?
)