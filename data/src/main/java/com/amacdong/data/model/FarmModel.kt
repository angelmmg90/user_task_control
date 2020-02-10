package com.amacdong.data.model

data class FarmModel (
    val farmName : String? = "",
    val category : String? = "",
    val item : String? = "",
    val farmerId : Int = 0,
    val website : WebsiteModel?,
    val zipcode : String? = "",
    val phone1 : String? = "",
    val business : String? = "",
    val l : Int? = 0,
    val location : LocationPointModel?
)