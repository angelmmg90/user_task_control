package com.amacdong.usertaskcontrol.data.database.entities

import androidx.room.*
import com.amacdong.data.model.TaskType

@Entity(
    tableName = "task_entity",
    indices = [Index("user_id", "id")],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onUpdate = ForeignKey.CASCADE
    )]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "duration") val duration: Int? = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "completed") val completed: Boolean = false,
    @ColumnInfo(name = "user_id") var userId: String
)