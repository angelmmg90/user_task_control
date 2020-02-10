package com.amacdong.usertaskcontrol.ui.features.farms

interface FarmContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: FarmViewModel.UiModel)
    }

    interface ViewModel {
        fun viewLoaded()
        suspend fun loadFarmData()
    }

}
