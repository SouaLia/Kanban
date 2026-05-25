package com.exemplo.cecilia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exemplo.cecilia.R
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.FragmentDoneBinding
import com.exemplo.cecilia.databinding.FragmentTodoBinding
import com.exemplo.cecilia.task.util.showBottomSheet
import com.exemplo.cecilia.ui.adapter.TaskAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.getValue

class DoneFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val viewModel: TaskViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reference = Firebase.database.reference
        auth = Firebase.auth

        initListener()
        initRecyclerViewTask()
        getTask()
    }
    private fun initListener() {
        binding.floatingActionButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }

        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if (updateTask.status == Status.TODO) {
                val oldList = taskAdapter.currentList

                val newList = oldList.map { task ->
                    if (task.id == updateTask.id) {
                        task.copy(description = updateTask.description)
                    } else {
                        task
                    }
                }

                val position = newList.indexOfFirst { it.id == updateTask.id }

                taskAdapter.submitList(newList)
                if (position != -1) {
                    taskAdapter.notifyItemChanged(position)
                }
            }
        }
    }
    private fun initRecyclerViewTask() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }
        binding.recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTask.setHasFixedSize(true)
        binding.recyclerViewTask.adapter = taskAdapter
    }
    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(titleDialog = R.string.text_title_dialog_delete,
                    message= getString(R.string.text_message_dialog_delete),
                    titleButton = R.string.text_button_dialog_confirm,
                    onClick = {
                        deleteTask(task)
                    }
                )
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(requireContext(), "Próximo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() {
        reference
            .child("task")
            .child(auth.currentUser?.uid ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val taskList = mutableListOf<Task>()

                    for (ds in p0.children) {
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.DONE) {
                            taskList.add(task)
                        }
                    }
                    binding.progressBar.isVisible = false
                    taskList.reverse()
                    listEmpty(taskList)
                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun deleteTask(task: Task){
        reference
            .child("task")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_delete_sucess_task, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun listEmpty(taskList: List<Task>) {
        binding.textInfo.text = if (taskList.isEmpty()){
            getString(R.string.text_list_task_empty)
        } else{
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}