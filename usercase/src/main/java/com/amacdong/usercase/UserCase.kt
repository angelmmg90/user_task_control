package com.amacdong.usercase

import androidx.lifecycle.LiveData
import com.amacdong.data.model.UserModel
import com.amacdong.data.repositories.UserRepository

class UserCase(private val userRepository: UserRepository) {
    suspend fun getUsers() : LiveData<List<UserModel>> =
        userRepository.getUsers()

    suspend fun insertUser(user: UserModel) : Long? =
        userRepository.insertUser(user)

    suspend fun signIn(userId: String, password: String) : UserModel? =
        userRepository.signIn(userId, password)

     suspend fun getUsersByHoursLeft() : UserModel? =
        userRepository.getUsersByHoursLeft()

    suspend fun updateUserHoursLeft(hoursLeft: Int, userId: String) : Boolean =
        userRepository.updateUserHoursLeft(hoursLeft, userId)

}