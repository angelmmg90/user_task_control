package com.amacdong.data.repositories

import androidx.lifecycle.LiveData
import com.amacdong.data.model.UserModel
import com.amacdong.data.sources.UserLocalDatasource

class UserRepository (
    private val userLocalDatasource: UserLocalDatasource
) {
    suspend fun getUsers(): LiveData<List<UserModel>> =
        userLocalDatasource.getUsers()

    suspend fun updateUserHoursLeft(hoursLeft: Int, userId: String): Boolean =
        userLocalDatasource.updateUserHoursLeft(hoursLeft, userId)

    suspend fun insertUser(user: UserModel): Long? =
        userLocalDatasource.insertUser(user)

    suspend fun signIn(userId: String, password: String): UserModel? =
        userLocalDatasource.signIn(userId, password)

    suspend fun getUsersByHoursLeft(): UserModel? =
        userLocalDatasource.getUsersByHoursLeft()
}