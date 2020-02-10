package com.amacdong.usertaskcontrol.ui.componentes.interfaces

interface OnCustomKOScreen {
    fun onShowKOScreen(imgName: String, imgHeight: Int, imgWidth: Int, errorMessage: String, sizeTextError: Float)
    fun onRestoreDefaultScreen()
    fun onShowKOScreen()
}
