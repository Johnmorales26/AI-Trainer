package com.johndev.aitrainer.Ranges.RangesMethodModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.ResultsPerceptronAdapter
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.R
import com.johndev.aitrainer.Ranges.RangesMethodActivity.Companion.rangesChargeDataViewModel
import com.johndev.aitrainer.Ranges.RangesMethodActivity.Companion.rangesViewModel
import com.johndev.aitrainer.common.entities.ResultsPerceptron
import com.johndev.aitrainer.databinding.FragmentRangesOperationsBinding

class RangesOperationsFragment : Fragment() {

    private var _binding: FragmentRangesOperationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterResults: ResultsPerceptronAdapter
    private lateinit var dataset: DatasetComplete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRangesOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupObservers() {
        rangesViewModel.getResultOperations().observe(viewLifecycleOwner) { result ->
            if (result == null) {

            } else {
                setupRecyclerView(result)
                playSound()
            }
        }
        rangesChargeDataViewModel.getTwoDimensionalResult().observe(viewLifecycleOwner) { datasetList ->
            if (datasetList == null) {
                Toast.makeText(context, "Regrese a cargar datos", Toast.LENGTH_SHORT).show()
            } else {
                val valuesX: MutableList<Double> = mutableListOf()
                val valuesY: MutableList<Double> = mutableListOf()
                datasetList.forEach {
                    valuesX.add(it.dataX)
                    valuesY.add(it.dataY)
                }
                dataset = DatasetComplete(valuesX, valuesY)
            }
        }
    }

    private fun setupButtons() {
        binding.btnCalcular.setOnClickListener {
            if (validFieldsRanges()){
                val valueB = binding.etValueB.text.toString().trim().toDouble()
                val valueW = binding.etValueW.text.toString().trim().toDouble()
                val rangeA = binding.etMaxRange.text.toString().trim().toDouble()
                val rangeB = binding.etMinRange.text.toString().trim().toDouble()
                rangesViewModel.getMethodRanges(valueW, valueB, rangeA, rangeB, dataset)
            }
        }
    }

    private fun setupRecyclerView(listResult: MutableList<ResultsPerceptron> = mutableListOf()) {
        listResult.reverse()
        binding.let {
            adapterResults = ResultsPerceptronAdapter(listResult)
            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterResults
            }
        }
    }

    private fun playSound() {
        val sound = MainActivity.sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            //MediaPlayer.create(context, MainActivity.directionSound).start()
        }
    }

    private fun validFieldsRanges(): Boolean {
        var isValid = true
        //  Evaluando value A
        if (binding.etValueB.text.isNullOrEmpty()){
            binding.tilValueB.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilValueB.error = null
        }
        // Evaluando value B
        if (binding.etValueW.text.isNullOrEmpty()){
            binding.tilValueW.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etValueW.error = null
        }
        // Evaluando value B
        if (binding.etMaxRange.text.isNullOrEmpty()){
            binding.tilMaxRange.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etMaxRange.error = null
        }
        // Evaluando value B
        if (binding.etMinRange.text.isNullOrEmpty()){
            binding.tilMinRange.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etMinRange.error = null
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}