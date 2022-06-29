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
import com.johndev.aitrainer.databinding.DialogLoadOperationsBinding

class DialogLoadOperationsFragment(private val onSubmitClickListener: (Float) -> Unit): DialogFragment() {

    private lateinit var binding: DialogLoadOperationsBinding
    private var handler = Handler()
    private var isActive = false
    private var progress = 0
    private var value = 1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogLoadOperationsBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        loadProgressBar()

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

    private fun loadProgressBar() {
        if (!isActive) {
            var thread = Thread(Runnable {
                kotlin.run {
                    while (progress <= 100) {
                        handler.post(Runnable {
                            kotlin.run {
                                if ((progress % 4) == 0){
                                    when (value) {
                                        1 -> {
                                            binding.tvCharge.text = "Cargando."
                                            value = 2
                                        }
                                        2 -> {
                                            binding.tvCharge.text = "Cargando.."
                                            value = 3
                                        }
                                        3 -> {
                                            binding.tvCharge.text = "Cargando..."
                                            value = 1
                                        }
                                    }
                                }
                                binding.tvChargePercent.text = "$progress%"
                                binding.progressBar.progress = progress
                            }
                        })
                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        if (progress == 100) {
                            dismiss()
                        }
                        progress++
                        isActive = true
                    }
                }
            })
            thread.start()
        }
    }

}