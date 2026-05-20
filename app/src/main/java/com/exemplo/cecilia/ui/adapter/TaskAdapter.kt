package com.exemplo.cecilia.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.cecilia.R
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val taskSelected: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val SELECT_BACK: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem && oldItem.description == newItem.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.textDescription.text = task.description

        setIndicators(task, holder)

        holder.binding.buttonBack.setOnClickListener {
            taskSelected(task, SELECT_BACK)
        }

        holder.binding.buttonForward.setOnClickListener {
            taskSelected(task, SELECT_NEXT)
        }
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        holder.binding.buttonBack.isVisible = true
        holder.binding.buttonForward.isVisible = true

        when (task.status) {
            Status.TODO -> {
                holder.binding.buttonBack.isVisible = false
            }
            Status.DOING -> {
                holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context, R.color.status_todo))
                holder.binding.buttonForward.setColorFilter(ContextCompat.getColor(context, R.color.status_done))
            }
            Status.DONE -> {
                holder.binding.buttonForward.isVisible = false
            }
        }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)
}