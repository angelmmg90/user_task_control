package com.amacdong.domain.userCase


data class UserDomain (
    val id: String,
    val name: String,
    val role: String,
    val password: String,
    val hoursLeft: Int

)