package com.amacdong.usertaskcontrol.data

import com.amacdong.data.model.*
import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.domain.farmUserCase.LocationPointDomain
import com.amacdong.domain.farmUserCase.WebsiteDomain
import com.amacdong.domain.userCase.UserDomain
import com.amacdong.usertaskcontrol.data.database.entities.*

fun UserDomain.toUserEntity(): UserEntity =
    UserEntity(
        id,
        name,
        role,
        password,
        hoursLeft
    )

fun UserDomain.toUserModel(): UserModel =
    UserModel(
        id,
        name,
        role,
        password,
        hoursLeft
    )

fun TaskEntity.toTaskModel(): TaskModel =
    TaskModel(
        id,
        name,
        description,
        duration,
        type,
        completed,
        userId
    )


fun TaskModel.toTaskEntity(): TaskEntity =
    TaskEntity(
        id,
        name,
        description,
        duration,
        type,
        completed,
        user_id
    )

fun UserEntity.toUserModel(): UserModel =
    UserModel(
        id,
        name,
        role,
        password,
        hoursLeft
    )

fun UserModel.toUserEntity(): UserEntity =
    UserEntity(
        id,
        name,
        role,
        password,
        hoursLeft
    )


fun FarmDomain.toFarmModel(): FarmModel =
    FarmModel(
        farm_name,
        category,
        item,
        farmer_id,
        website?.toWebsiteModel(),
        zipcode,
        phone1,
        business,
        l,
        location_1?.toLocationPointModel()
    )

fun FarmDomain.toFarmEntity(): FarmEntity =
    FarmEntity(
        farmer_id,
        farm_name,
        category,
        item,
        website?.toWebsiteEntity(),
        zipcode,
        phone1,
        business,
        l,
        location_1?.toLocationPointEntity()
    )

fun FarmEntity.toFarmModel(): FarmModel =
    FarmModel(
        farmName,
        category,
        item,
        id,
        website?.toWebsiteModel(),
        zipcode,
        phone1,
        business,
        l,
        location1?.toLocationPointModel()
    )

fun WebsiteEntity.toWebsiteModel(): WebsiteModel =
    WebsiteModel(
        url
    )

fun LocationPointEntity.toLocationPointModel(): LocationPointModel =
    LocationPointModel(
        latitude = latitude,
        longitude = longitude,
        humanAddress = humanAddress
    )

fun WebsiteDomain?.toWebsiteModel(): WebsiteModel =
    WebsiteModel(
        url = this?.url
    )

fun WebsiteDomain?.toWebsiteEntity(): WebsiteEntity =
    WebsiteEntity(
        url = this?.url
    )

fun LocationPointDomain.toLocationPointEntity(): LocationPointEntity =
    LocationPointEntity(
        latitude = latitude,
        longitude = longitude,
        humanAddress = human_address
    )

fun LocationPointDomain.toLocationPointModel(): LocationPointModel =
    LocationPointModel(
        latitude,
        longitude,
        human_address
    )


fun FarmModel.toFarmEntity(): FarmEntity =
    FarmEntity(
        farmerId,
        farmName,
        category,
        item,
        website?.toWebsiteEntity(),
        zipcode,
        phone1,
        business,
        l,
        location?.toLocationPointModel()
    )

fun WebsiteModel?.toWebsiteEntity(): WebsiteEntity =
    WebsiteEntity(
        url = this?.url
    )

fun LocationPointModel?.toLocationPointModel(): LocationPointEntity =
    LocationPointEntity(
        latitude = this?.latitude,
        longitude = this?.longitude,
        humanAddress = this?.humanAddress
    )

