package com.amacdong.usertaskcontrol.data.networking.results

import com.google.gson.annotations.SerializedName

data class LocationPointResult (
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("human_address") val humanAddress : String
)