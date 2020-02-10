package com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks

interface CompletedTasksContract {
    interface View {
        fun initializeViews()
        fun updateUi(model: CompletedTasksViewModel.UiModel)
    }

    interface ViewModel {
        fun viewLoaded(userId: String)
        suspend fun getCompletedTasks(userId: String)
    }

}
