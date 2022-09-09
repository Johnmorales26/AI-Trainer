package com.johndev.aitrainer.Automatic.AutomaticCalculoModule.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Automatic.AutomaticActivity.Companion.automaticChargeDataViewModel
import com.johndev.aitrainer.Automatic.AutomaticActivity.Companion.automaticOpViewModel
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.common.entities.Automatic
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.utils.checkOnlyNumbers
import com.johndev.aitrainer.databinding.FragmentAutomaticCalculoBinding
import com.johndev.aitriner.Adapters.AutomaticAdapter
import kotlinx.coroutines.*
import java.util.*

class AutomaticCalculoFragment : Fragment() {

    private var _binding: FragmentAutomaticCalculoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AutomaticAdapter
    private lateinit var dataset: DatasetComplete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticCalculoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtons()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        automaticChargeDataViewModel.getTwoDimensionalResult()
            .observe(viewLifecycleOwner) { datasetList ->
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
        automaticOpViewModel.getResultAutomatic().observe(viewLifecycleOwner) { resultsPerceptron ->
            if (resultsPerceptron == null) {

            } else {
                binding.etValueIterations.text =
                    resultsPerceptron[resultsPerceptron.size - 1].id.toString().trim().editable()
                binding.etValueR.text =
                    resultsPerceptron[resultsPerceptron.size - 1].R.toString().trim().editable()
                binding.etValueJ.text =
                    resultsPerceptron[resultsPerceptron.size - 1].J.toString().trim().editable()
                binding.etValueW0.text =
                    resultsPerceptron[resultsPerceptron.size - 1].W0.toString().trim().editable()
                binding.etValueW1.text =
                    resultsPerceptron[resultsPerceptron.size - 1].W1.toString().trim().editable()
            }
        }
    }

    private fun configureButtons() {
        binding.btnCalcular.setOnClickListener {
            val umbral = binding.etUmbral.text!!.toString().trim()
            if (checkOnlyNumbers(umbral)){
                with(binding) {
                    etValueW0.text = getString(R.string.label_load).trim().editable()
                    etValueW1.text = getString(R.string.label_load).trim().editable()
                    etValueIterations.text = getString(R.string.label_load).trim().editable()
                    etValueJ.text = getString(R.string.label_load).trim().editable()
                    etValueR.text = getString(R.string.label_load).trim().editable()
                    lifecycleScope.launch(Dispatchers.IO) {
                        automaticOpViewModel.automaticMethodForDiferences(umbral.toDouble(), dataset)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(printList: MutableList<Automatic> = mutableListOf()) {
        binding.let {
            adapter = AutomaticAdapter(printList)
            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@AutomaticCalculoFragment.adapter
            }
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun Random.nextInt(range: IntRange): Int {
        return range.first + nextInt(range.last - range.first)
    }

}