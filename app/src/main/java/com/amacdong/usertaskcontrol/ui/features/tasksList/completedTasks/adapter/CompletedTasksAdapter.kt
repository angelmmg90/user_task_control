package com.amacdong.usertaskcontrol.ui.features.tasksList.completedTasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amacdong.data.model.TaskModel
import com.amacdong.usertaskcontrol.R
import kotlinx.android.synthetic.main.item_completed_task.view.*
import kotlinx.android.synthetic.main.item_task.view.*

class CompletedTasksAdapter(
    val context: Context,
    private val listTask: List<TaskModel>,
    private val clickListener: (TaskModel, View) -> Unit
    ) : RecyclerView.Adapter<CompletedTasksAdapter.TaskItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_completed_task, parent, false)

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
            itemView.tvTaskNameCompleted.text = taskItem.name
            itemView.tvTaskDescriptionCompleted.text = taskItem.description
            itemView.setOnClickListener {
                clickListener(taskItem, view)
            }
        }
    }
}
