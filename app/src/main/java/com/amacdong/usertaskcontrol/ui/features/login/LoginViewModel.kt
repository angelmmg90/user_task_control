package com.amacdong.usertaskcontrol.ui.features.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amacdong.data.model.FarmModel
import com.amacdong.data.repositories.Response
import com.amacdong.domain.farmUserCase.FarmDomain
import com.amacdong.usertaskcontrol.data.networking.results.FarmResult
import com.amacdong.usercase.FarmUserCase
import com.amacdong.usercase.UserCase
import com.amacdong.usertaskcontrol.common.ScopedViewModel
import com.amacdong.usertaskcontrol.data.Session
import com.google.gson.Gson
import kotlinx.coroutines.*

class LoginViewModel (
    private val ctx: Application,
    private val userCase: UserCase
): ScopedViewModel(), LoginContract.ViewModel {

    private lateinit var signInJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        object GoToSystem : UiModel()
        object ErrorSignIn : UiModel()

    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::signInJob.isInitialized && signInJob.isActive) {
            signInJob.cancel()
        }
    }

    override fun signIn(userId: String, password: String) {
        signInJob = CoroutineScope(Dispatchers.IO).launch {
            var userReceived = userCase.signIn(userId, password)

            if (userReceived == null) {
                withContext(Dispatchers.Main) {
                    _model.value = UiModel.ErrorSignIn
                }
            } else {
                withContext(Dispatchers.Main) {
                    Session.saveUserRole(ctx, userReceived.role)
                    Session.saveUserObject(ctx, Gson().toJson(userReceived))
                    _model.value = UiModel.GoToSystem
                }
            }
        }
    }

}