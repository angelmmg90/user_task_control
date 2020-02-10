package com.amacdong.usertaskcontrol.ui.features.newTask

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.usercase.TaskUserCase
import com.amacdong.usercase.UserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*

class NewTaskViewModel (
    private val ctx: Application,
    private val taskUserCase: TaskUserCase,
    private val userCase: UserCase

): ScopedViewModel(), NewTaskContract.ViewModel {


    private var idNewTask: Long? = null
    private var assignedTask: Boolean = false
    private var updatedUser: Boolean = false
    private var userForTask: UserModel? = null
    private lateinit var insertTaskJob: Job
    private lateinit var getUserForTaskJob: Job
    private lateinit var assignTaskToUserJob: Job
    private lateinit var updatedUserHourLeftJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class GetAppropiateUserForTask(val task: TaskModel) : UiModel()
        class AssignTaskToUser(val user: UserModel, val task: TaskModel) : UiModel()
        class UpdatedUserHourLeft(val user: UserModel, val task: TaskModel) : UiModel()
        class AssignedTask(val user: UserModel, val task: TaskModel) : UiModel()
        object BdError : UiModel()
        object UserNotHaveEnoughHourLeft : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::insertTaskJob.isInitialized && insertTaskJob.isActive) {
            insertTaskJob.cancel()
        }

        if (::getUserForTaskJob.isInitialized && getUserForTaskJob.isActive) {
            getUserForTaskJob.cancel()
        }
        if (::assignTaskToUserJob.isInitialized && assignTaskToUserJob.isActive) {
            assignTaskToUserJob.cancel()
        }
    }

    override fun insertNewTask(task: TaskModel) {
        insertTaskJob = CoroutineScope(Dispatchers.IO).launch {
            idNewTask = taskUserCase.insertTask(task)

            if (idNewTask == null) {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.BdError
                }
            } else {
                var newTaskWithID = TaskModel(
                    idNewTask!!.toInt(),
                    task.name,
                    task.description,
                    task.duration,
                    task.type,
                    task.completed,
                    task.user_id
                )

                withContext(Dispatchers.Main) {
                    _model.value = UiModel.GetAppropiateUserForTask(newTaskWithID)
                }
            }
        }
    }

    override fun getAppropiateUserForTask(task: TaskModel) {
        getUserForTaskJob = CoroutineScope(Dispatchers.IO).launch {
            userForTask = userCase.getUsersByHoursLeft()

            if (userForTask == null) {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.BdError
                }
            } else {
                withContext(Dispatchers.Main) {
                    userForTask?.let{
                        _model.value = UiModel.UpdatedUserHourLeft(it, task)
                    }
                }
            }
        }
    }

    override fun updateUserHoursLeft(user: UserModel, task: TaskModel) {
        updatedUserHourLeftJob = CoroutineScope(Dispatchers.IO).launch {
            var updatedUserHourLeft:Int = user.hoursLeft.minus(task.duration!!)

            if(updatedUserHourLeft >= 0){
                updatedUser = userCase.updateUserHoursLeft(updatedUserHourLeft, user.id)

                if(updatedUser){
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.AssignTaskToUser(user, task)
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        _model.value =
                            UiModel.BdError
                    }
                }
            }else{
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.UserNotHaveEnoughHourLeft
                }
            }

        }
    }

    override fun assignTaskToUser(user: UserModel, task: TaskModel) {
        assignTaskToUserJob = CoroutineScope(Dispatchers.IO).launch {
            assignedTask = taskUserCase.assignTaskToUser(user, task)

            if (assignedTask) {
                withContext(Dispatchers.Main) {
                    _model.value =
                        UiModel.AssignedTask(user, task)
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