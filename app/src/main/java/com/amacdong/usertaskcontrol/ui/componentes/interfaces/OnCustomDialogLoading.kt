package com.amacdong.usertaskcontrol.ui.componentes.interfaces

interface OnCustomDialogLoading: OnCustomDialogLoadingSpinner {
    val isShowingCustomDialogLoading: Boolean
    fun hideComponent()
}