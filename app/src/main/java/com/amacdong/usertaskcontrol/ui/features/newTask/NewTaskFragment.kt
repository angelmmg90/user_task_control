package com.amacdong.usertaskcontrol.ui.features.newTask


import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel

import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.data.Session
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_new_task.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class NewTaskFragment : Fragment(), NewTaskContract.View {

    private val viewModel: NewTaskViewModel by currentScope.viewModel(this)
    private lateinit var currentUser: UserModel
    private lateinit var newTaskCretaed: TaskModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeEvents()
    }


    override fun initializeViews() {
        Session.getUserObject(context!!)?.let {
            currentUser = Gson().fromJson(it, UserModel::class.java)
        }
    }

    override fun goToTaskList() {
        val taskListScreen = NewTaskFragmentDirections.goTaskListAction()
        findNavController().navigate(taskListScreen)
    }

    override fun initializeEvents() {
        tvCreateNewTask.setOnClickListener {
            var insertNewTask: Boolean = true
            val shake = AnimationUtils.loadAnimation(this.requireContext(), R.anim.shake_error)
            if(edtTaskName.text.toString().isNullOrEmpty()){
                insertNewTask = false
                edtTaskName.startAnimation(shake)
            }

            if(edtTaskDescription.text.toString().isNullOrEmpty()){
                insertNewTask = false
                edtTaskDescription.startAnimation(shake)
            }

            if(edtTaskTime.text.toString().isNullOrEmpty()){
                insertNewTask = false
                edtTaskTime.startAnimation(shake)
            }

            if(spnTaskTypes.selectedItem.toString().isNullOrEmpty()){
                insertNewTask = false
                spnTaskTypes.startAnimation(shake)
            }

            if(insertNewTask){
                newTaskCretaed = TaskModel(
                    name =  edtTaskName.text.toString(),
                    description = edtTaskDescription.text.toString(),
                    duration = edtTaskTime.text.toString().toInt(),
                    type = spnTaskTypes.selectedItem.toString(),
                    user_id = currentUser.id
                )

                viewModel.insertNewTask(newTaskCretaed)
            }else{
                Toast.makeText(context, getString(R.string.check_field_new_task), Toast.LENGTH_LONG).show()
            }

        }
        tvCancelNewTask.setOnClickListener {
            goToTaskList()
        }
    }

    override fun updateUi(model: NewTaskViewModel.UiModel) = when(model) {
        is NewTaskViewModel.UiModel.BdError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.error_adding_new_task),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        is NewTaskViewModel.UiModel.GetAppropiateUserForTask -> {
            viewModel.getAppropiateUserForTask(model.task)
        }
        is NewTaskViewModel.UiModel.AssignTaskToUser -> {
            viewModel.assignTaskToUser(model.user, model.task)
        }
        is NewTaskViewModel.UiModel.AssignedTask -> {
            val toast = Toasty.info(
                context!!,
                model.task.name.capitalize() + " " + getString(R.string.assigned_to) + " " + model.user.name,
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
            goToTaskList()

        }
        is NewTaskViewModel.UiModel.UpdatedUserHourLeft -> {
            viewModel.updateUserHoursLeft(model.user, model.task)
        }
        NewTaskViewModel.UiModel.UserNotHaveEnoughHourLeft -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_user_available_for_task),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
            goToTaskList()
        }
    }

}
