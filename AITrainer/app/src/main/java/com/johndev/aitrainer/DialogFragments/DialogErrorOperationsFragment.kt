package com.johndev.aitrainer.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.johndev.aitrainer.databinding.DialogDetailsDataBinding.inflate
import com.johndev.aitrainer.databinding.DialogErrorOperationsBinding
import com.johndev.aitrainer.databinding.DialogLoadOperationsBinding

class DialogErrorOperationsFragment(private val onSubmitClickListener: (Float) -> Unit): DialogFragment() {

    private lateinit var binding: DialogErrorOperationsBinding
    private var handler = Handler()
    private var isActive = false
    private var progress = 0
    private var value = 1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogErrorOperationsBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.btnAcept.setOnClickListener { dismiss() }

        val dialog = builder.create()
        val nightModeFlags = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        return dialog
    }

}