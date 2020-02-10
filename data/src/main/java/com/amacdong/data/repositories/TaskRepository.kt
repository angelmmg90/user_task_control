package com.amacdong.data.repositories

import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.data.sources.TaskLocalDatasource

class TaskRepository (
    private val taskLocalDatasource: TaskLocalDatasource
) {
    suspend fun getAllTasks(): List<TaskModel> =
        taskLocalDatasource.getAllTasks()

    suspend fun inserTask(taskToInsert: TaskModel): Long? =
        taskLocalDatasource.insertTask(taskToInsert)

    suspend fun getAllCompletedTasksByUserID(userID: String): List<TaskModel> =
        taskLocalDatasource.getAllCompletedTasksByUserID(userID)

    suspend fun getAllPendingTasksByUserID(userID: String): List<TaskModel>  =
        taskLocalDatasource.getAllPendingTasksByUserID(userID)

    suspend fun updateStatusTask(status: Boolean, taskID: String): Boolean =
        taskLocalDatasource.updateStatusTask(status, taskID)

    suspend fun assignTaskToUser(user: UserModel,  task: TaskModel): Boolean =
        taskLocalDatasource.assignTaskToUser(user, task)

}