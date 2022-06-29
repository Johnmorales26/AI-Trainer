package com.johndev.aitrainer.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.johndev.aitrainer.DialogFragments.PassData.PASS_COSTO
import com.johndev.aitrainer.DialogFragments.PassData.PASS_ID
import com.johndev.aitrainer.DialogFragments.PassData.PASS_JW
import com.johndev.aitrainer.DialogFragments.PassData.PASS_W
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.DialogDetailsDataBinding


class DialogDetailsDataFragment(private val onSubmitClickListener: (Float) -> Unit): DialogFragment() {

    private lateinit var binding: DialogDetailsDataBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDetailsDataBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val extras = arguments
        val id = extras!!.getInt(PASS_ID)
        val W = extras!!.getFloat(PASS_W)
        val JW = extras!!.getFloat(PASS_JW)
        val COSTO = extras!!.getFloat(PASS_COSTO)

        binding.apply {
            tvNumIterations.text = "Iteration ${id.toString().trim()}"
            tvValueJW.text = "El valor de J(w) es: ${JW.toString().trim()}"
            tvValueW.text = "El valor de W es: ${W.toString().trim()}"
        }
        /*val values = mutableListOf<Entry>(Entry(W, COSTO))
        configureGraphic(values)*/

        binding.btnExit.setOnClickListener { dismiss() }

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

    /*private fun configureGraphic(valuesGrap: MutableList<Entry>) {
        val yValues: MutableList<Entry> = valuesGrap
        val setOne = LineDataSet(yValues, "Costo Contra W")

        setOne.apply {
            fillAlpha = 110
            color = R.color.secondaryColor
            lineWidth = 3f
            valueTextSize = 10f
            valueTextColor = R.color.secondaryTextColor
        }

        val dataSets: MutableList<ILineDataSet> = mutableListOf()
        dataSets.add(setOne)
        val data = LineData(dataSets)

        binding.apply {
            chartLine.setTouchEnabled(true)
            chartLine.setPinchZoom(true)
            chartLine.isDragEnabled = true
            chartLine.setScaleEnabled(false)
            chartLine.data = data
            chartLine.animateXY(1000, 1500)
        }
    }*/

}