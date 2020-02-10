package com.amacdong.domain.farmUserCase


data class FarmDomain (
    val farm_name : String? = "",
    val category : String? = "",
    val item : String? = "",
    val farmer_id : Int,
    val website : WebsiteDomain? = WebsiteDomain(),
    val zipcode : String? = "",
    val phone1 : String? = "",
    val business : String? = "",
    val l : Int? = 0,
    val location_1 : LocationPointDomain? = LocationPointDomain()
)