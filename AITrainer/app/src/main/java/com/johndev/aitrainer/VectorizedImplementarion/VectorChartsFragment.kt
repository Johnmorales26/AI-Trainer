package com.johndev.aitrainer.VectorizedImplementarion

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.johndev.aitrainer.Models.Automatic
import com.johndev.aitrainer.Models.ChartVectorData
import com.johndev.aitrainer.R
import com.johndev.aitrainer.VectorizedImplementarion.VectorOperationsFragment.Companion.chartVectorData
import com.johndev.aitrainer.databinding.FragmentVectorChartsBinding
import com.johndev.aitrainer.databinding.FragmentVectorOperationsBinding
import com.johndev.aitrainer.regresion_automatic.AutomaticCalculoFragment

class VectorChartsFragment : Fragment() {

    private var _binding: FragmentVectorChartsBinding? = null
    private val binding get() = _binding!!
    private var valuesCosto: MutableList<Entry> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVectorChartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortChartData(1)
    }

    private fun setupChart() {
        val yValues: MutableList<Entry> = valuesCosto
        val setOne = LineDataSet(yValues, context?.getString(R.string.name_chart_j_epoch))

        setOne.apply {
            fillAlpha = 110
            lineWidth = 5f
            valueTextSize = 10f
            setDrawCircles(false)
            color = ContextCompat.getColor(requireContext(), R.color.primaryColor)
            valueTextColor = R.color.background
        }

        val dataSets: MutableList<ILineDataSet> = mutableListOf()
        dataSets.add(setOne)
        val data = LineData(dataSets)

        with(binding) {
            chartLine.apply {
                setDrawBorders(true)
                setBorderColor(Color.BLACK)
                setBorderWidth(2F)
                val description = Description()
                description.apply {
                    text = context.getString(R.string.name_chart_j_epoch)
                    textColor = Color.BLACK
                    textSize = 15F
                }
                setDescription(description)
                setNoDataText(context.getString(R.string.chart_no_exist_data))
                setTouchEnabled(true)
                setPinchZoom(true)
                isDragEnabled = true
                setScaleEnabled(false)
                chartLine.data = LineData(mutableListOf())
                chartLine.data = data
                animateXY(3500, 3500)
            }
        }
    }

    private fun sortChartData(limit: Int = 500) {
        val chartData = chartVectorData
        val sortChartData: MutableList<ChartVectorData> = mutableListOf()
        var x = 0
        while (x < chartData.size) {
            if ((x % limit) == 0) sortChartData.add(chartData[x])
            x++
        }
        configureValues(sortChartData)
    }

    private fun configureValues(sortChartData: MutableList<ChartVectorData>) {
        for (results in sortChartData){
            valuesCosto.add(Entry(results.epoch.toFloat(), results.valueJ.toFloat()))
        }
        setupChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}