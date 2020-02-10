package com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks

interface PendingTasksContract {
    interface View {
        fun initializeViews()
        fun updateStatusTask()
        fun updateUi(model: PendingTasksViewModel.UiModel)
    }

    interface ViewModel {
        fun viewLoaded(userId: String)
        fun modifyStatusTask(taskCompleted:Boolean, taskId: String)
        suspend fun getPendingTasks(userId: String)
        suspend fun updateStatusTask(status: Boolean, taskId: String)
    }

}
