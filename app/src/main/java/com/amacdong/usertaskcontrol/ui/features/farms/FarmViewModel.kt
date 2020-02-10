package com.amacdong.usertaskcontrol.ui.features.farms

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.FarmModel
import com.amacdong.data.repositories.Response
import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.usertaskcontrol.data.networking.results.FarmResult
import com.amacdong.usercase.FarmUserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import com.amacdong.usertaskcontrol.data.toFarmModel
import kotlinx.coroutines.*

class FarmViewModel (
    private val ctx: Application,
    private val farmUserCase: FarmUserCase
): ScopedViewModel(), FarmContract.ViewModel {

    private lateinit var farmData: List<FarmModel>
    private lateinit var getFarmsJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowFarms(val listFarms: List<FarmModel>) : UiModel()
        object Forbbiden : UiModel()
        object NetWorkError : UiModel()

    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getFarmsJob.isInitialized && getFarmsJob.isActive) {
            getFarmsJob.cancel()
        }
    }

    override fun viewLoaded() {
        farmData = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            loadFarmData()
        }
    }

    override suspend fun loadFarmData() {
        var response: Response<Array<FarmDomain>>? = null

        getFarmsJob = CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.IO) {
                farmData = farmUserCase.getFarmsFromLocal()
            }
            if (farmData.isNullOrEmpty()) {

                withContext(Dispatchers.IO) {
                    response = farmUserCase.getFarmsFromRemote()
                }
                when (response) {
                    is Response.NetWorkError -> {
                        withContext(Dispatchers.Main) {
                            _model.value =
                                UiModel.NetWorkError
                        }
                    }
                    is Response.Forbidden -> {
                        withContext(Dispatchers.Main) {
                            _model.value =
                                UiModel.Forbbiden
                        }
                    }
                    is Response.Success -> {
                        var listFarmsModel = ArrayList<FarmModel>()
                        var rawListFarms = (response as Response.Success<Array<FarmDomain>>).data
                        rawListFarms.forEach {
                            listFarmsModel.add(it.toFarmModel())
                        }

                        withContext(Dispatchers.Main) {
                            farmUserCase.persistFarmsIntoDatabase(listFarmsModel)
                            _model.value = UiModel.ShowFarms(listFarmsModel)
                        }

                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.ShowFarms(farmData)
                }
            }

        }
    }

    fun loadFarmsData(){

    }



}