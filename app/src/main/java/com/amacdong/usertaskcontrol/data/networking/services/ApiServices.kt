package com.amacdong.usertaskcontrol.data.networking.services

import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.usertaskcontrol.data.networking.results.FarmResult
import retrofit2.http.*

interface ApiServices {

    @GET("hma6-9xbg.json")
    suspend fun getFarms(
        @Query("category") category: String,
        @Query("item") item: String
    ): Array<FarmDomain>

}
