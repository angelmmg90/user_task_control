package com.amacdong.usertaskcontrol.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amacdong.usertaskcontrol.data.database.entities.FarmEntity
import com.amacdong.usertaskcontrol.data.database.entities.TaskEntity

@Dao
interface FarmDAO {
    @Query("SELECT * from farm_entity")
    fun getAllFarms(): List<FarmEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFarms(farm: List<FarmEntity>)

}