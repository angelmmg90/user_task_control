package com.amacdong.usertaskcontrol.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "location_point_entity"
)
data class LocationPointEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "location_point_id") val id: Int = 0,
    @ColumnInfo(name = "latitude") val latitude: Double? = 0.0,
    @ColumnInfo(name = "longitude") val longitude: Double? = 0.0,
    @ColumnInfo(name = "human_address") val humanAddress: String? = ""
)