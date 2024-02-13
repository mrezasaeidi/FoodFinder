package com.saeidi.baseapplication.storage.repository.local.dao

import androidx.room.*
import com.saeidi.baseapplication.storage.repository.local.entity.UserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Query("DELETE FROM user")
    fun deleteUsers()

    @Query("SELECT * FROM user WHERE id=:id")
    suspend fun getUser(id: Int): UserModel?
}