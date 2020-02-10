package com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.usercase.TaskUserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import kotlinx.coroutines.*

class PendingTasksViewModel (
    private val ctx: Application,
    private val taskUserCase: TaskUserCase
): ScopedViewModel(),
    PendingTasksContract.ViewModel {

    private lateinit var tasksData: List<TaskModel>
    private lateinit var getTasksJob: Job
    private var updateTask: Boolean = false

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowTasks(val listFarms: List<TaskModel>) : UiModel()
        object BdError : UiModel()
        object NotFoundTasks : UiModel()
        object UpdatedTaskStatus : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getTasksJob.isInitialized && getTasksJob.isActive) {
            getTasksJob.cancel()
        }
    }

    override fun viewLoaded(userId: String) {
        tasksData = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            getPendingTasks(userId)
        }
    }

    override fun modifyStatusTask(taskCompleted: Boolean, taskId: String) {
        tasksData = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            updateStatusTask(taskCompleted, taskId)
        }
    }

    override suspend fun getPendingTasks(userId: String) {
        withContext(Dispatchers.IO) {
            tasksData = taskUserCase.getAllPendingTasksByUserID(userId)
        }
        if (tasksData.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                _model.value =
                    UiModel.NotFoundTasks
            }
        } else {
            withContext(Dispatchers.Main) {
                _model.value =
                    UiModel.ShowTasks(
                        tasksData
                    )
            }
        }
    }

    override suspend fun updateStatusTask(status: Boolean, taskId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            updateTask = taskUserCase.updateStatusTask(status, taskId)

            if (updateTask) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.UpdatedTaskStatus
                }
            } else {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.BdError
                }
            }
        }
    }

}