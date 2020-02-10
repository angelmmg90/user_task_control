package com.amacdong.usertaskcontrol.data.networking.results

import com.google.gson.annotations.SerializedName

data class FarmResult (
    @SerializedName("farm_name") val farmName : String,
    @SerializedName("category") val category : String,
    @SerializedName("item") val item : String,
    @SerializedName("farmer_id") val farmerId : Int,
    @SerializedName("website") val website : WebsiteResult,
    @SerializedName("zipcode") val zipcode : Int,
    @SerializedName("phone1") val phone1 : String,
    @SerializedName("business") val business : String,
    @SerializedName("l") val l : Int,
    @SerializedName("location_1") val location : LocationPointResult
)