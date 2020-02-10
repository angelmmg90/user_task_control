package com.amacdong.data.sources

import com.amacdong.data.repositories.Response
import com.amacdong.domain.farmUserCase.FarmDomain

interface FarmRemoteDatasource {
    suspend fun getFarmsFromRemote(): Response<Array<FarmDomain>>
}