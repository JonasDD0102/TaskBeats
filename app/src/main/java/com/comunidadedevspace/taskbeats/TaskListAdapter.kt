package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(
    private val openTaskdateilView:(task:Task) -> Unit
    ):
   ListAdapter<Task,TaskListViewHolder>(TaskListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view:View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.iten_task,parent,false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task,openTaskdateilView)
    }
  companion object : DiffUtil.ItemCallback<Task>(){

      override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
         return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
          return oldItem.title == newItem.title &&
                 oldItem.description == newItem.description
      }
  }
}

class TaskListViewHolder(
    private val view:View)
    : RecyclerView.ViewHolder(view){
  private val textTitle = view.findViewById<TextView>(R.id.tv_task_title)
  private val textDescription = view.findViewById<TextView>(R.id.tv_task_description)

  fun bind(
      task:Task,
      openTaskdateilView: (task: Task) -> Unit
  ) {
      textTitle.text = task.title
      textDescription.text = "${task.id}- ${task.description}"

      view.setOnClickListener{
          openTaskdateilView.invoke(task)
      }
  }
}