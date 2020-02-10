package com.amacdong.usertaskcontrol.ui.features.login

interface LoginContract {
    interface View {
        fun initializeEvents()
        fun initializeViews()
        fun updateUi(model: LoginViewModel.UiModel)
        fun accessToSystem()
        fun errorSignIn()
        fun showProgressDialog()
        fun dismissProgressDialog()
        fun inflateMenu()
    }

    interface ViewModel {
        fun signIn(userId: String, password: String)
    }

}
