package com.amacdong.usertaskcontrol.data.networking.results

import com.google.gson.annotations.SerializedName

data class WebsiteResult (
    @SerializedName("url") val url : String
)