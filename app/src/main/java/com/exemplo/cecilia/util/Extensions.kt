package com.exemplo.cecilia.task.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.exemplo.cecilia.R
import com.exemplo.cecilia.databinding.BottonSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title=""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}

fun Fragment.showBottonSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    mensagem: Int,
    onClick: () -> Unit ={}
){/* ERRO 134
    val bottomSheetDialog = BottomSheetDialog(requireContext())
    val binding: BottomSheetBinding =
        BottomSheetBinding.inflate( layoutInflater, null, false)
    binding.textviewTitle.text= getText(titleDialog ?: R.string.text_title_warning )
    binding.textviewMessage.text = getText(mensagem)
    binding.buttonOk.text = getText(titleButton ?: R.string.text_button_warning)
    binding.buttonOK.setOnClickListener{
        onClick()
        bottomSheetDialog.dismiss()
    }
}*/