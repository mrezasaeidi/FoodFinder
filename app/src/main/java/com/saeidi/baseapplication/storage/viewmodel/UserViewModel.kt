package com.saeidi.baseapplication.storage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saeidi.baseapplication.storage.repository.local.LocalDatabase
import com.saeidi.baseapplication.storage.repository.local.entity.UserModel

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = LocalDatabase.getDatabase(application).userDao()

    suspend fun getUser(userId: Int): UserModel? {
        return userDao.getUser(userId)
    }
}