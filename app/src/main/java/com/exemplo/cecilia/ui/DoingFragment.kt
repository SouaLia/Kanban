package com.exemplo.cecilia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exemplo.cecilia.R
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.FragmentDoingBinding
import com.exemplo.cecilia.databinding.FragmentLoginBinding
import com.exemplo.cecilia.databinding.FragmentTodoBinding

class DoingFragment : Fragment() {
    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getTask() = listOf(
        Task("3", "Configurar autenticação Firebase", Status.DOING),
        Task("4", "Implementar ProgressBar no carregamento", Status.DOING),
        Task("5", "Estilizar itens da lista de tarefas", Status.DOING),
        Task("6", "Corrigir bugs de navegação", Status.DOING),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}