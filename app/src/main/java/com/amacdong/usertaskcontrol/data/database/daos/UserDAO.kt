package com.amacdong.usertaskcontrol.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amacdong.usertaskcontrol.data.database.entities.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * from user_entity")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * from user_entity WHERE role = 'technical' ORDER BY hours_left DESC LIMIT 1")
    fun getUsersByHoursLeft(): UserEntity

    @Query("UPDATE user_entity SET hours_left = :hoursLeft WHERE id = :userId")
    fun updateUserHoursLeft(hoursLeft: Int, userId: String)

    @Query("SELECT * from user_entity WHERE id = :userId AND password = :password")
    fun signIn(userId: String, password: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("DELETE FROM user_entity")
    suspend fun deleteAll()


}