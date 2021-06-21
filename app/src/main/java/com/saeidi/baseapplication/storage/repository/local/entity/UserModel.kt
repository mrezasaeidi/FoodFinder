package com.saeidi.baseapplication.storage.repository.local.entity

import androidx.room.Entity

@Entity(tableName = "user")
data class UserModel(val id: Int, val name: String, val avatarUrl: String?)