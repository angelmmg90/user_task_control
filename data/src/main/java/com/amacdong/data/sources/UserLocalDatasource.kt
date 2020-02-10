package com.amacdong.data.sources

import androidx.lifecycle.LiveData
import com.amacdong.data.model.UserModel

interface UserLocalDatasource {
    suspend fun getUsers(): LiveData<List<UserModel>>
    suspend fun insertUser(user: UserModel): Long?
    suspend fun signIn(userId: String, password: String): UserModel?
    suspend fun getUsersByHoursLeft(): UserModel?
    suspend fun updateUserHoursLeft(hoursLeft: Int, userId: String): Boolean
}