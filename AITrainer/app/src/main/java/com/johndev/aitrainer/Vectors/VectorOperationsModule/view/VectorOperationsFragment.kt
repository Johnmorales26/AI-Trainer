package com.johndev.aitrainer.Vectors.VectorOperationsModule.view

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.entities.PrintDataVectores
import com.johndev.aitrainer.Vectors.VectorsActivity.Companion.vectorDatasetViewModel
import com.johndev.aitrainer.Vectors.VectorsActivity.Companion.vectorOperationsViewModel
import com.johndev.aitrainer.common.adapters.PrintDataVectoresAdapter
import com.johndev.aitrainer.common.entities.ChargeVectorData
import com.johndev.aitrainer.databinding.FragmentVectorOperationsBinding
import com.johndev.aitrainer.format.configureDecimals

class VectorOperationsFragment : Fragment() {

    private var _binding: FragmentVectorOperationsBinding? = null
    private val binding get() = _binding!!
    private var umbral: Double? = null
    private var printData: MutableList<PrintDataVectores> = mutableListOf()
    private lateinit var adapter: PrintDataVectoresAdapter
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var chargeData: ChargeVectorData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVectorOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        setupObservers()
        configureUmbral()
        configureButtons()
    }

    private fun setupObservers() {
        vectorDatasetViewModel.getSortedDatasetResult().observe(viewLifecycleOwner) { sortedData ->
            if (sortedData == null) {
                Toast.makeText(context, "No Existen Datos", Toast.LENGTH_SHORT).show()
            } else {
                chargeData = sortedData
            }
        }
        vectorOperationsViewModel.getResultOperations().observe(viewLifecycleOwner) { result ->
            if (result == null) {
                binding.btnCalcular.isEnabled = true
                Toast.makeText(context, "Error al realizar operaciones", Toast.LENGTH_SHORT).show()
            } else {
                binding.btnCalcular.isEnabled = true
                val type = sharedPreferences.getString(getString(R.string.key_preference_decimal_numbers),
                    getString(R.string.preferences_two_decimals))
                val index = result.paintResults.size - 1
                with(binding) {
                    tvTitleWs.visibility = VISIBLE
                    updateRecyclerView(type, result.wResults)
                    etValueJ.isEnabled = true
                    etValueJ.text = configureDecimals(type!!, result.paintResults[index].J).editable()
                    etValueIterations.isEnabled = true
                    etValueIterations.text = result.paintResults[index].index.toString().trim().editable()
                    etValueR.isEnabled = true
                    etValueR.text = configureDecimals(type, result.paintResults[index].R).editable()
                }
            }
        }
    }

    private fun configureUmbral() {
        binding.etUmbral.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                umbral = if (binding.etUmbral.text.toString().trim().isBlank()) {
                    binding.etUmbral.text = "1.0".trim().editable()
                    1.0
                } else {
                    binding.etUmbral.text.toString().trim().toDouble()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                binding.btnCalcular.isEnabled = !binding.etUmbral.text.isNullOrEmpty()
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun configureButtons() {
        binding.btnCalcular.setOnClickListener {
            binding.btnCalcular.isEnabled = false
            paintLoadData()
            vectorOperationsViewModel.vectorCalculatios(umbral ?: 0.0, chargeData.ArrayX, chargeData.ArrayY, chargeData.ArrayW)
        }
    }

    private fun paintLoadData() {
        with(binding) {
            etValueJ.text = getString(R.string.label_load).trim().editable()
            etValueR.text = getString(R.string.label_load).trim().editable()
            etValueIterations.text = getString(R.string.label_load).trim().editable()
        }
    }

    private fun updateRecyclerView(type: String?, arrayUpdateW: Array<DoubleArray>) {
        binding.let {
            adapter = PrintDataVectoresAdapter(arrayUpdateW, type.toString())
            it.recyclerView.apply {
                //layoutManager = LinearLayoutManager(context)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@VectorOperationsFragment.adapter
            }
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

}