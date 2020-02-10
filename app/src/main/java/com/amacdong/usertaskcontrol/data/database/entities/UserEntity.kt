package com.amacdong.usertaskcontrol.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_entity",
    indices = [Index( "id")]
)
data class UserEntity (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "role") val role: String = "",
    @ColumnInfo(name = "password") val password: String = "",
    @ColumnInfo(name = "hours_left") val hoursLeft: Int = 0

)