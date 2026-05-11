package com.exemplo.cecilia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exemplo.cecilia.R
import com.exemplo.cecilia.data.model.Status
import com.exemplo.cecilia.data.model.Task
import com.exemplo.cecilia.databinding.FragmentDoneBinding
import com.exemplo.cecilia.databinding.FragmentTodoBinding

class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getTask() = listOf(
        Task ("0", "Criar nova tela do app", Status.DONE),
        Task ("1", "Validar informações de login", Status.DONE),
        Task ("2", "Adicionar nova funcionalidade no app", Status.DONE),
    )
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}