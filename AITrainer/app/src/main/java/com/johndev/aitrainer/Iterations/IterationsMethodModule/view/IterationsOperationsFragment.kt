package com.johndev.aitrainer.Iterations.IterationsMethodModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.ResultsPerceptronAdapter
import com.johndev.aitrainer.Iterations.IterationsMethodActivity.Companion.chargeDataViewModel
import com.johndev.aitrainer.Iterations.IterationsMethodActivity.Companion.iterationsViewModel
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.common.entities.ResultsPerceptron
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.FragmentIterationsOperationsBinding

class IterationsOperationsFragment : Fragment() {

    private var _binding: FragmentIterationsOperationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dataset: DatasetComplete
    private lateinit var adapterResults: ResultsPerceptronAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIterationsOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupObservers() {
        chargeDataViewModel.getTwoDimensionalResult().observe(viewLifecycleOwner) { datasetList ->
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
        iterationsViewModel.getResultOperations().observe(viewLifecycleOwner) { result ->
            if (result == null) {

            } else {
                setupRecyclerView(result)
            }
        }
    }

    private fun setupButtons() {
        binding.btnCalcular.setOnClickListener {
            if (validFieldsIterator()){
                val valueB = binding.etValueB.text.toString().trim().toDouble()
                val valueW = binding.etValueW.text.toString().trim().toDouble()
                val iterations = binding.etIterations.text.toString().trim().toDouble()
                iterationsViewModel.getMethodIterator(valueB, valueW, iterations, dataset)
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

    private fun validFieldsIterator(): Boolean{
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
        if (binding.etIterations.text.isNullOrEmpty()){
            binding.tilIterations.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etIterations.error = null
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
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}