package com.exemplo.cecilia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.ItemTaskBinding

// Adicionei o (private val taskList: List<Task>) aqui
class TaskAdapter(
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

    inner class MyViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)
}