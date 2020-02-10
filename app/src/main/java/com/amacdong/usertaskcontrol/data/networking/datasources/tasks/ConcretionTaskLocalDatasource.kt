package com.amacdong.usertaskcontrol.data.networking.datasources.tasks

import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.data.sources.TaskLocalDatasource
import com.amacdong.usertaskcontrol.data.database.UserTaskControlDatabase
import com.amacdong.usertaskcontrol.data.toTaskEntity
import com.amacdong.usertaskcontrol.data.toTaskModel

class ConcretionTaskLocalDatasource(private var db: UserTaskControlDatabase): TaskLocalDatasource {


    override suspend fun getAllCompletedTasksByUserID(userID: String): List<TaskModel> {
        val listCompletedTask = ArrayList<TaskModel>()

        db.taskDAO().getAllCompletedTasksByUserID(userID).forEach {
            listCompletedTask.add(it.toTaskModel())
        }

        return listCompletedTask
    }

    override suspend fun getAllPendingTasksByUserID(userID: String): List<TaskModel> {
        val listPendingTasks = ArrayList<TaskModel>()

        db.taskDAO().getAllPendingTasksByUserID(userID).forEach {
            listPendingTasks.add(it.toTaskModel())
        }

        return listPendingTasks
    }

    override suspend fun updateStatusTask(status: Boolean, taskID: String): Boolean {
        return try {
            db.taskDAO().updateStatusTask(status, taskID)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun assignTaskToUser(user: UserModel, task: TaskModel): Boolean {
        return try {
            db.taskDAO().assignTaskToUser(user.id, task.id.toString())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getAllTasks(): List<TaskModel> {
        val listTaskModel = ArrayList<TaskModel>()

        db.taskDAO().getAllTasks().forEach {
            listTaskModel.add(it.toTaskModel())
        }

        return listTaskModel
    }

    override suspend fun insertTask(taskToInsert: TaskModel): Long? {
        return try {
            db.taskDAO().insertTask(taskToInsert.toTaskEntity())
        } catch (e: Exception) {
            null
        }
    }

}

