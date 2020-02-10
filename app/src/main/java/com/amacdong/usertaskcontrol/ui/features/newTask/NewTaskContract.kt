package com.amacdong.usertaskcontrol.ui.features.newTask

import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel

interface NewTaskContract {
    interface View {
        fun initializeViews()
        fun initializeEvents()
        fun updateUi(model: NewTaskViewModel.UiModel)
        fun goToTaskList()
    }

    interface ViewModel {
        fun insertNewTask(task: TaskModel)
        fun getAppropiateUserForTask(task: TaskModel)
        fun assignTaskToUser(user: UserModel, task: TaskModel)
        fun updateUserHoursLeft(user: UserModel, task: TaskModel)
    }

}
