package com.amacdong.usertaskcontrol.di

import android.app.Application
import com.amacdong.data.repositories.FarmRepository
import com.amacdong.data.repositories.TaskRepository
import com.amacdong.data.repositories.UserRepository
import com.amacdong.data.sources.FarmLocalDatasource
import com.amacdong.data.sources.FarmRemoteDatasource
import com.amacdong.data.sources.TaskLocalDatasource
import com.amacdong.data.sources.UserLocalDatasource
import com.amacdong.usercase.FarmUserCase
import com.amacdong.usercase.TaskUserCase
import com.amacdong.usercase.UserCase
import com.amacdong.usertaskcontrol.data.database.UserTaskControlDatabase
import com.amacdong.usertaskcontrol.data.networking.datasources.farms.ConcretionFarmLocalDatasource
import com.amacdong.usertaskcontrol.data.networking.datasources.farms.ConcretionFarmRemoteDatasource
import com.amacdong.usertaskcontrol.data.networking.datasources.tasks.ConcretionTaskLocalDatasource
import com.amacdong.usertaskcontrol.data.networking.datasources.users.ConcretionUserLocalDatasource
import com.amacdong.usertaskcontrol.ui.features.farms.FarmFragment
import com.amacdong.usertaskcontrol.ui.features.farms.FarmViewModel
import com.amacdong.usertaskcontrol.ui.features.login.LoginFragment
import com.amacdong.usertaskcontrol.ui.features.login.LoginViewModel
import com.amacdong.usertaskcontrol.ui.features.newTask.NewTaskFragment
import com.amacdong.usertaskcontrol.ui.features.newTask.NewTaskViewModel
import com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks.ListTasksFragment
import com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks.ListTasksViewModel
import com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks.CompletedTasksFragment
import com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks.CompletedTasksViewModel
import com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks.PendingTasksFragment
import com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks.PendingTasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(
            appModule,
            dataModule,
            scopesModule)
        )
    }
}

private val appModule = module {
    single { UserTaskControlDatabase.build(get()) }
    single<FarmRemoteDatasource> { ConcretionFarmRemoteDatasource(get()) }
    single<FarmLocalDatasource> { ConcretionFarmLocalDatasource(get()) }
    single<UserLocalDatasource> { ConcretionUserLocalDatasource(get()) }
    single<TaskLocalDatasource> { ConcretionTaskLocalDatasource(get()) }
}

private val dataModule = module {
    factory { UserRepository(get()) }
    factory { FarmRepository(get(), get()) }
    factory { TaskRepository(get()) }
}

private val scopesModule = module {

    scope((named<FarmFragment>())) {
        scoped { FarmUserCase(get()) }
        viewModel { FarmViewModel(get(), get()) }
    }

    scope((named<LoginFragment>())) {
        scoped { UserCase(get()) }
        viewModel { LoginViewModel(get(), get()) }
    }

    scope((named<ListTasksFragment>())) {
        scoped { TaskUserCase(get()) }
        viewModel {
            ListTasksViewModel(
                get(),
                get()
            )
        }
    }

    scope((named<NewTaskFragment>())) {
        scoped { TaskUserCase(get()) }
        scoped { UserCase(get()) }
        viewModel { NewTaskViewModel(get(), get(), get()) }
    }

    scope((named<CompletedTasksFragment>())) {
        scoped { TaskUserCase(get()) }
        viewModel {
            CompletedTasksViewModel(
                get(),
                get()
            )
        }
    }

    scope((named<PendingTasksFragment>())) {
        scoped { TaskUserCase(get()) }
        viewModel {
            PendingTasksViewModel(
                get(),
                get()
            )
        }
    }
}

