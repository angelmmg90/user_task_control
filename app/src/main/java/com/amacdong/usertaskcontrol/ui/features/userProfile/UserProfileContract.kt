package com.amacdong.usertaskcontrol.ui.features.userProfile

interface UserProfileContract {
    interface View {
        fun initializeEvents()
        fun initializeViews()
        fun logout()
    }

    interface ViewModel {
    }

}
