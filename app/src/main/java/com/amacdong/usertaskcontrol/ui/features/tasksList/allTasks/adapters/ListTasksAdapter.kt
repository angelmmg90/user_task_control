package com.amacdong.usertaskcontrol.ui.features.tasksList.allTasks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amacdong.data.model.TaskModel
import com.amacdong.usertaskcontrol.R
import kotlinx.android.synthetic.main.item_task.view.*

class ListTasksAdapter(
    val context: Context,
    private val listTask: List<TaskModel>,
    private val clickListener: (TaskModel, View) -> Unit
    ) : RecyclerView.Adapter<ListTasksAdapter.TaskItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)

        return TaskItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listTask.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(listTask[position], clickListener)
    }

    class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindTaskItem(
            taskItem: TaskModel,
            clickListener: (TaskModel, View) -> Unit
        ) {
            itemView.tvTaskName.text = taskItem.name
            itemView.tvTaskDescription.text = taskItem.description
            itemView.setOnClickListener {
                clickListener(taskItem, view)
            }
        }
    }
}
