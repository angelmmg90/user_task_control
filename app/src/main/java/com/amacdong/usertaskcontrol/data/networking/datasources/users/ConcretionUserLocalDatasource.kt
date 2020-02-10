package com.amacdong.usertaskcontrol.data.networking.datasources.users

import androidx.lifecycle.LiveData
import com.amacdong.data.model.UserModel
import com.amacdong.data.sources.UserLocalDatasource
import com.amacdong.usertaskcontrol.data.database.UserTaskControlDatabase
import com.amacdong.usertaskcontrol.data.toUserModel
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.usertaskcontrol.data.toTaskModel
import com.amacdong.usertaskcontrol.data.toUserEntity


class ConcretionUserLocalDatasource(private var db: UserTaskControlDatabase): UserLocalDatasource {

    override suspend fun getUsers(): LiveData<List<UserModel>>{
        val listUserModel = ArrayList<UserModel>()
        val liveDataUserModel =  MutableLiveData<List<UserModel>>()

        db.userDAO().getAllUsers().forEach {
            listUserModel.add(it.toUserModel())
        }

        liveDataUserModel.value = listUserModel

        return liveDataUserModel
    }

    override suspend fun getUsersByHoursLeft(): UserModel? {
        return  db.userDAO().getUsersByHoursLeft().toUserModel()
    }

    override suspend fun updateUserHoursLeft(hoursLeft: Int, userId: String): Boolean {
        return try {
            db.userDAO().updateUserHoursLeft(hoursLeft, userId)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun insertUser(user: UserModel): Long? {
        return try {
            db.userDAO().insertUser(user.toUserEntity())
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signIn(userId: String, password: String): UserModel? {
        return try {
            db.userDAO().signIn(userId, password).toUserModel()
        } catch (e: Exception) {
            null
        }
    }

}

