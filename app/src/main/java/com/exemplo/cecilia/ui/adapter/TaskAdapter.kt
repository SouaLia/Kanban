package com.exemplo.cecilia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.ItemTaskBinding
import android.content.Context
import com.exemplo.cecilia.R

class TaskAdapter(
    private val context: Context,
    private val taskList: List<Task>
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Ajustei para LayoutInflater.from (com L maiúsculo)
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textDescription.text = task.description
    }

    private fun setIndicators(task: Task, holder: MyViewHolder){
        when(task.status){
            Status.TODO -> {
                holder.binding.buttonBack.isVisible = false
            }
            Status.DOING -> {
                holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context,R.color.status_todo))
                holder.binding.buttonForward.setColorFilter(ContextCompat.getColor(context,R.color.status_done))
            }
            Status.DONE -> {
                holder.binding.buttonForward.isVisible = false
            }
        }
    }
    inner class MyViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)
}