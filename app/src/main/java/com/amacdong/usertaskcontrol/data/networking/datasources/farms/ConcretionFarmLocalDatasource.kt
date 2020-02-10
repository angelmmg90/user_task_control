package com.amacdong.usertaskcontrol.data.networking.datasources.farms

import com.amacdong.data.model.FarmModel
import com.amacdong.data.repositories.Response
import com.amacdong.data.sources.FarmLocalDatasource
import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.usertaskcontrol.data.database.UserTaskControlDatabase
import com.amacdong.usertaskcontrol.data.database.entities.FarmEntity
import com.amacdong.usertaskcontrol.data.toFarmEntity
import com.amacdong.usertaskcontrol.data.toFarmModel

class ConcretionFarmLocalDatasource(private var db: UserTaskControlDatabase) : FarmLocalDatasource {
    private lateinit var farms: Response<Array<FarmDomain>>

    override suspend fun persistFarmsIntoDatabase(farmsDomain: List<FarmModel>): Boolean {
        val farmsEntityList = ArrayList<FarmEntity>()

        return try {
            farmsDomain.forEach {
                farmsEntityList.add(it.toFarmEntity())
            }

            db.farmDAO().insertFarms(farmsEntityList)
            return true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    override suspend fun getFarmsFromLocal(): List<FarmModel> {
        val listFarmModel = ArrayList<FarmModel>()

        db.farmDAO().getAllFarms().forEach {
            listFarmModel.add(it.toFarmModel())
        }

        return listFarmModel
    }

}

