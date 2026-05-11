package com.exemplo.cecilia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exemplo.cecilia.R
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.FragmentHomeBinding
import com.exemplo.cecilia.databinding.FragmentLoginBinding
import com.exemplo.cecilia.databinding.FragmentTodoBinding
import com.exemplo.cecilia.ui.adapter.TaskAdapter

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

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
        initListener()

        initRecyclerViewTask(getTask())
    }
    private fun initListener(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    private fun initRecyclerViewTask(taskList: List<Task>){
        taskAdapter = TaskAdapter(requireContext(),taskList)
        binding.recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTask.setHasFixedSize(true)

        binding.recyclerViewTask.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("9", "Testar persistência de dados", Status.TODO),
        Task("10", "Otimizar tempo de resposta da API", Status.TODO),
        Task("11", "Documentar métodos da classe Adapter", Status.TODO),
        Task("12", "Implementar exclusão de tarefas", Status.TODO),
        Task("13", "Finalizar design do Kanban", Status.TODO)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}