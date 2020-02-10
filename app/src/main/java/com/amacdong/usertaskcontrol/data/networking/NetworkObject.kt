package com.amacdong.usertaskcontrol.data.networking

import com.amacdong.usertaskcontrol.BuildConfig
import com.amacdong.usertaskcontrol.data.networking.services.ApiServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkObject {
    const val BASE_URL =  BuildConfig.BASEURL

    val service: ApiServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create(ApiServices::class.java)
        }
}