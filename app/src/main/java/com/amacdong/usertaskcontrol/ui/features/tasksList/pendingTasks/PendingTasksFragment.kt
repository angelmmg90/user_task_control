package com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks


import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amacdong.data.model.TaskModel
import com.amacdong.data.model.UserModel
import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.data.Session
import com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks.adapter.PendingTasksAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_pending_tasks.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 */
class PendingTasksFragment : Fragment(),
    PendingTasksContract.View {

    private val viewModel: PendingTasksViewModel by currentScope.viewModel(this)


    private lateinit var adapter: PendingTasksAdapter
    private lateinit var pendingTasks: ArrayList<TaskModel>
    private lateinit var taskDoneCallback: ItemTouchHelper.Callback

    companion object {
        const val TAG = "PendingTasksFragment"
        fun newInstance(): PendingTasksFragment {
            return PendingTasksFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun updateStatusTask() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val taskDoneIcon = resources.getDrawable(
            R.drawable.ic_done,
            null
        )

        taskDoneCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var taskToComplete = pendingTasks[viewHolder.adapterPosition]
                var taskToCompletePositionInList = viewHolder.adapterPosition

                adapter.removeItem(viewHolder.adapterPosition)
                viewModel.modifyStatusTask(true, taskToComplete.id.toString())

                Snackbar.make(
                    this@PendingTasksFragment.requireView(),
                    getString(R.string.task_done),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.undo_task)) {
                    adapter.restoreItem(taskToComplete, taskToCompletePositionInList)
                    viewModel.modifyStatusTask(false, taskToComplete.id.toString())
                }.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                c.clipRect(
                    0f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )

                if (dX < viewHolder.itemView.width / 3) {
                    c.drawColor(ContextCompat.getColor(context!!, R.color.dove_gray))
                } else {
                    c.drawColor(ContextCompat.getColor(context!!, R.color.primary))
                }


                val textMargin = resources.getDimension(R.dimen.item_detail_margin)
                    .roundToInt() + 20

                taskDoneIcon.bounds = Rect(
                    textMargin,
                    viewHolder.itemView.top + textMargin,
                    textMargin + taskDoneIcon.intrinsicWidth,
                    viewHolder.itemView.top + taskDoneIcon.intrinsicHeight + textMargin
                )

                taskDoneIcon.draw(c)

            }


        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_tasks, container, false)
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
        rvTasksPending.layoutManager = LinearLayoutManager(context!!)
        (ItemTouchHelper(taskDoneCallback)).attachToRecyclerView(rvTasksPending)
    }

    override fun updateUi(model: PendingTasksViewModel.UiModel) = when (model) {
        is PendingTasksViewModel.UiModel.ShowTasks -> {
            pendingTasks = model.listFarms as ArrayList<TaskModel>
            adapter = PendingTasksAdapter(
                context!!,
                pendingTasks
            ) { taskItem: TaskModel, _: View ->

                Toasty.info(
                    context!!,
                    taskItem.name,
                    Toast.LENGTH_SHORT,
                    true
                ).show()

            }
            rvTasksPending.adapter = adapter
        }
        PendingTasksViewModel.UiModel.BdError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.error_getting_task_message),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        PendingTasksViewModel.UiModel.UpdatedTaskStatus -> {
            /*val toast = Toast.makeText(
                activity,
                "Updated task",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.TOP, 0, 70)
            toast.show()*/
        }
        PendingTasksViewModel.UiModel.NotFoundTasks -> {
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
