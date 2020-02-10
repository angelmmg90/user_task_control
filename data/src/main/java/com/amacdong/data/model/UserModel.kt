package com.amacdong.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    val id: String = "",
    val name: String = "",
    val role: String = RoleType.technical.role,
    val password: String= "",
    val hoursLeft: Int = 0
): Parcelable