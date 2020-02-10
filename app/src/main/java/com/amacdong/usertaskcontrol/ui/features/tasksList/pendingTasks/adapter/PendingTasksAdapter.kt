package com.amacdong.usertaskcontrol.ui.features.tasksList.pendingTasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amacdong.data.model.TaskModel
import com.amacdong.usertaskcontrol.R
import kotlinx.android.synthetic.main.item_pending_task.view.*




class PendingTasksAdapter(
    val context: Context,
    private val listTask: ArrayList<TaskModel>,
    private val clickListener: (TaskModel, View) -> Unit
) : RecyclerView.Adapter<PendingTasksAdapter.TaskItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_pending_task, parent, false)

        return TaskItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listTask.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(listTask[position], clickListener)
    }

    fun removeItem(position: Int) {
        listTask.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: TaskModel, position: Int) {
        listTask.add(position, item)
        notifyItemInserted(position)
    }

    class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindTaskItem(
            taskItem: TaskModel,
            clickListener: (TaskModel, View) -> Unit
        ) {
            itemView.tvTaskNamePending.text = taskItem.name
            itemView.tvTaskDescriptionPending.text = taskItem.description
            itemView.setOnClickListener {
                clickListener(taskItem, view)
            }
        }
    }


}
