package com.amacdong.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskModel (
    val id: Int = 0,
    val name: String="",
    val description: String="",
    val duration: Int? = 0,
    val type: String = TaskType.etc.type,
    val completed: Boolean = false,
    var user_id: String
) : Parcelable