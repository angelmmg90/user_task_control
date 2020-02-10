package com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amacdong.data.model.TaskModel
import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks.adapters.ListTasksAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_list_tasks.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ListTasksFragment : Fragment(),
    ListTasksContract.View {

    private val viewModel: ListTasksViewModel by currentScope.viewModel(this)


    private lateinit var adapter: ListTasksAdapter
    private lateinit var listTasks: ArrayList<TaskModel>

    companion object {
        const val TAG = "ListTaskFragment"
        fun newInstance(): ListTasksFragment {
            return ListTasksFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_tasks_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addNewTask -> {
                createNewTask()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun createNewTask() {
        val newTaskScreen =
            ListTasksFragmentDirections.goCreateNewTaskAction()
        findNavController().navigate(newTaskScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.viewLoaded()
    }


    override fun initializeViews() {
        rvTasks.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: ListTasksViewModel.UiModel) = when(model) {
        is ListTasksViewModel.UiModel.ShowTasks -> {
            listTasks = model.listFarms as ArrayList<TaskModel>
            adapter = ListTasksAdapter(
                context!!,
                listTasks
            ) { taskItem: TaskModel, _: View ->
                val toast = Toasty.info(
                    context!!,
                    taskItem.name,
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()
            }
            rvTasks.adapter = adapter
        }
        ListTasksViewModel.UiModel.BdError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.error_getting_task_message),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
    }


}
