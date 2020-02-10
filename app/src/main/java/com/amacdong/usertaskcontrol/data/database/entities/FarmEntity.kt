package com.amacdong.usertaskcontrol.data.database.entities

import androidx.room.*

@Entity(
    tableName = "farm_entity"
)
data class FarmEntity (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "farmer_id") val id: Int = 0,
    @ColumnInfo(name = "farm_name") val farmName: String? = "",
    @ColumnInfo(name = "category") val category: String? = "",
    @ColumnInfo(name = "item") val item: String? = "",
    @Embedded val website: WebsiteEntity?,
    @ColumnInfo(name = "zipcode") val zipcode: String? = "",
    @ColumnInfo(name = "phone1") val phone1: String? = "",
    @ColumnInfo(name = "business") val business: String? = "",
    @ColumnInfo(name = "l") val l: Int? = 0,
    @Embedded val location1: LocationPointEntity?
)