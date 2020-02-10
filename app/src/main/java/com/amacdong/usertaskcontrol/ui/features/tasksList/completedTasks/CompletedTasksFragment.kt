package com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.data.Session
import com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks.adapter.CompletedTasksAdapter
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_completed_tasks.*
import kotlinx.android.synthetic.main.fragment_list_tasks.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class CompletedTasksFragment : Fragment(),
    CompletedTasksContract.View {

    private val viewModel: CompletedTasksViewModel by currentScope.viewModel(this)

    private lateinit var adapter: CompletedTasksAdapter
    private lateinit var completedTasks: ArrayList<TaskModel>


    companion object {
        const val TAG = "CompletedTasksFragment"
        fun newInstance(): CompletedTasksFragment {
            return CompletedTasksFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        Session.getUserObject(context!!)?.let {
            var userWithSession = Gson().fromJson(it, UserModel::class.java)
            viewModel.viewLoaded(userWithSession.id)
        }
    }

    override fun initializeViews() {
        rvTasksCompleted.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: CompletedTasksViewModel.UiModel) = when(model) {
        is CompletedTasksViewModel.UiModel.ShowTasks -> {
            completedTasks = model.listFarms as ArrayList<TaskModel>
            adapter = CompletedTasksAdapter(
                context!!,
                completedTasks
            ) { taskItem: TaskModel, view: View ->
                Toast.makeText(
                    context,
                    taskItem.name,
                    Toast.LENGTH_LONG
                ).show()
            }
            rvTasksCompleted.adapter = adapter
        }
        CompletedTasksViewModel.UiModel.BdError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.error_getting_task_message),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        CompletedTasksViewModel.UiModel.NotFoundTasks -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_found_tasks),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
    }
}
