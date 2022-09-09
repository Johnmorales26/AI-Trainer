package com.johndev.aitrainer.Automatic

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.johndev.aitrainer.Automatic.AutomaticActivity.Companion.automaticOpViewModel
import com.johndev.aitrainer.common.entities.Automatic
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.FragmentChartBinding

class AutomaticChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private var valuesCosto: MutableList<Entry> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        automaticOpViewModel.getResultAutomatic().observe(viewLifecycleOwner) { result ->
            if (result == null) {

            } else {
                sortChartData(20, result)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupChart() {
        val yValues: MutableList<Entry> = valuesCosto
        val setOne = LineDataSet(yValues, context?.getString(R.string.name_chart_j_epoch))

        var colorArray: MutableList<Int> = mutableListOf(requireContext().getColor(R.color.color1),
            requireContext().getColor(R.color.color2),
            requireContext().getColor(R.color.color3),
            requireContext().getColor(R.color.color4),
            requireContext().getColor(R.color.color5))

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
                //setBackgroundColor(resources.getColor(R.color.line_chart_color_1))
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sortChartData(limit: Int = 500, result: MutableList<Automatic>) {
        val sortChartData: MutableList<Automatic> = mutableListOf()
        var x = 0
        while (x < result.size) {
            if ((x % limit) == 0) sortChartData.add(result[x])
            x++
        }
        configureValues(sortChartData)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun configureValues(sortChartData: MutableList<Automatic>) {
        for (results in sortChartData){
            valuesCosto.add(Entry(results.id.toFloat(), results.J.toFloat()))
        }
        setupChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}