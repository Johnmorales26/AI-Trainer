package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.view

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.DatasetAdapter
import com.johndev.aitrainer.Interfaces.OnDatasetListener
import com.johndev.aitrainer.common.entities.Dataset
import com.johndev.aitrainer.R
import com.johndev.aitrainer.MainActivity.Companion.sharedPreferences
import com.johndev.aitrainer.Ranges.RangesMethodActivity.Companion.rangesChargeDataViewModel
import com.johndev.aitrainer.databinding.FragmentChargeDatasetsBinding
import kotlinx.coroutines.launch

class RangesChargeDatasetsFragment : Fragment(), OnDatasetListener {

    private var _binding: FragmentChargeDatasetsBinding? = null
    private val binding get() = _binding!!
    private lateinit var datasetAdapter: DatasetAdapter
    private val pickPdfFile = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChargeDatasetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtons()
        setupObservers()
    }

    private fun setupObservers() {
        rangesChargeDataViewModel.getTwoDimensionalResult().observe(viewLifecycleOwner) { dataset ->
            if (dataset == null) {
                Toast.makeText(context, "Cargue los datos", Toast.LENGTH_SHORT).show()
            } else {
                setupRecyclerView(dataset)
            }
        }
    }

    private fun configureButtons() {
        dataset = mutableListOf()
        valuesX = mutableListOf()
        valuesY = mutableListOf()
        binding.btnChargeData.setOnClickListener {
            selectCSVFile()
            binding.clData.visibility = VISIBLE
            binding.tvAnnouncement.visibility = VISIBLE
        }

        binding.btnClearData.setOnClickListener {
            dataset.clear()
            valuesX.clear()
            valuesY.clear()
            setupRecyclerView(dataset)
        }
    }

    private fun selectCSVFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), pickPdfFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == pickPdfFile
            && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also { uri ->
                lifecycleScope.launch {
                    rangesChargeDataViewModel.readTextFromUri(uri, requireContext())
                }
            }
        }
    }

    private fun setupRecyclerView(dataset: MutableList<Dataset>) {
        datasetAdapter = DatasetAdapter(dataset, this)
        binding.rvChargeData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = datasetAdapter
        }
    }

    companion object {
        lateinit var valuesX: MutableList<Double>
        lateinit var valuesY: MutableList<Double>
        lateinit var dataset: MutableList<Dataset>
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}