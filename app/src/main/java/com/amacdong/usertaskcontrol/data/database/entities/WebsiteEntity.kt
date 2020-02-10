package com.amacdong.usertaskcontrol.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "website_entity"
)
data class WebsiteEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "website_id") val id: Int = 0,
    @ColumnInfo(name = "url") val url: String? = ""
)