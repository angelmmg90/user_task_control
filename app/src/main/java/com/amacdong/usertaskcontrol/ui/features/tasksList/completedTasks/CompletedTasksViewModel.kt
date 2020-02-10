package com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.usercase.TaskUserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import kotlinx.coroutines.*

class CompletedTasksViewModel (
    private val ctx: Application,
    private val taskUserCase: TaskUserCase
): ScopedViewModel(),
    CompletedTasksContract.ViewModel {


    private lateinit var tasksData: List<TaskModel>
    private lateinit var getTasksJob: Job


    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowTasks(val listFarms: List<TaskModel>) : UiModel()
        object BdError : UiModel()
        object NotFoundTasks : UiModel()
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
            getCompletedTasks(userId)
        }
    }

    override suspend fun getCompletedTasks(userId: String) {
        withContext(Dispatchers.IO) {
            tasksData = taskUserCase.getAllCompletedTasksByUserID(userId)
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

}