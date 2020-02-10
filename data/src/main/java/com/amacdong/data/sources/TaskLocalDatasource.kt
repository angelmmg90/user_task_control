package com.amacdong.data.sources

import androidx.lifecycle.LiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel

interface TaskLocalDatasource {
    suspend fun getAllTasks(): List<TaskModel>
    suspend fun insertTask(taskToInsert: TaskModel): Long?
    suspend fun getAllCompletedTasksByUserID(userID: String): List<TaskModel>
    suspend fun getAllPendingTasksByUserID(userID: String): List<TaskModel>
    suspend fun updateStatusTask(status: Boolean, taskID: String): Boolean
    suspend fun assignTaskToUser(user: UserModel, task: TaskModel): Boolean
}