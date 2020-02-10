package com.amacdong.usercase

import androidx.lifecycle.LiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.data.repositories.TaskRepository

class TaskUserCase(private val taskRepository: TaskRepository) {
    suspend fun getAllTasks() : List<TaskModel> =
        taskRepository.getAllTasks()

    suspend fun insertTask(task: TaskModel) : Long? =
        taskRepository.inserTask(task)

    suspend fun getAllCompletedTasksByUserID(userID: String) :  List<TaskModel> =
        taskRepository.getAllCompletedTasksByUserID(userID)

    suspend fun getAllPendingTasksByUserID(userID: String) :  List<TaskModel> =
        taskRepository.getAllPendingTasksByUserID(userID)

    suspend fun updateStatusTask(status: Boolean, taskID: String)  =
        taskRepository.updateStatusTask(status, taskID)

    suspend fun assignTaskToUser(user: UserModel, task: TaskModel)  =
        taskRepository.assignTaskToUser(user, task)


}
