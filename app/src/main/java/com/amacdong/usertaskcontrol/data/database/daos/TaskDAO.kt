package com.amacdong.usertaskcontrol.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amacdong.usertaskcontrol.data.database.entities.TaskEntity
import com.amacdong.usertaskcontrol.data.database.entities.UserEntity
import androidx.room.Update



@Dao
interface TaskDAO {
    @Query("SELECT * from task_entity ORDER BY name ASC")
    fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * from task_entity WHERE user_id = :userID AND completed = 1")
    fun getAllCompletedTasksByUserID(userID: String): List<TaskEntity>

    @Query("SELECT * from task_entity WHERE user_id = :userID AND completed = 0")
    fun getAllPendingTasksByUserID(userID: String): List<TaskEntity>

    @Query("UPDATE task_entity SET completed = :status WHERE id = :taskID")
    fun updateStatusTask(status: Boolean, taskID: String)

    @Query("UPDATE task_entity SET user_id = :userId WHERE id = :taskID")
    fun assignTaskToUser(userId: String, taskID: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskEntity): Long

    @Query("DELETE FROM task_entity")
    suspend fun deleteAll()
}