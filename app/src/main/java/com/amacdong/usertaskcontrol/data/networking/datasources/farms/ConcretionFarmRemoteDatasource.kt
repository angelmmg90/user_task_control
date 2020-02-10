package com.amacdong.usertaskcontrol.data.networking.datasources.farms

import android.content.Context
import com.amacdong.data.repositories.Response
import com.amacdong.data.sources.FarmRemoteDatasource
import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.usertaskcontrol.data.networking.NetworkObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import kotlin.coroutines.resume

class ConcretionFarmRemoteDatasource(val context: Context): FarmRemoteDatasource {
    private lateinit var farms: Response<Array<FarmDomain>>

    override suspend fun getFarmsFromRemote(): Response<Array<FarmDomain>> =
        suspendCancellableCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = NetworkObject.service.getFarms(
                        "Fruit",
                        "Peaches"
                    )

                    if (response != null) {
                        farms = Response.Success(response)
                        continuation.resume(farms)
                    } else {
                        continuation.resume(Response.Error(Throwable()))
                    }
                } catch (e: Exception) {
                    farms = when (e) {
                        is HttpException -> {
                            val isForbbiden = e.code() == 403 || e.code() == 401
                            if (isForbbiden) {
                                Response.Forbidden
                            } else {
                                Response.NetWorkError
                            }
                        }
                        else -> {
                            Response.NetWorkError
                        }
                    }
                    continuation.resume(farms)
                }
            }
        }
    }

