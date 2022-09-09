package com.johndev.aitrainer.Vectors.VectorChargeDataModule.view

import com.johndev.aitrainer.dataAccess.OperationsWithArrays
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.johndev.aitrainer.Vectors.VectorsActivity.Companion.vectorDatasetViewModel
import com.johndev.aitrainer.common.adapters.VectorDatasetsAdapter
import com.johndev.aitrainer.databinding.FragmentVectorDatasetsBinding
import kotlinx.coroutines.launch

class VectorDatasetsFragment : Fragment() {

    private var _binding: FragmentVectorDatasetsBinding? = null
    private val binding get() = _binding!!
    private val pickPdfFile = 1
    private lateinit var adapter: VectorDatasetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVectorDatasetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        configureButtons()
    }

    private fun setupObservers() {
        vectorDatasetViewModel.getDatasetResult().observe(viewLifecycleOwner) { dataset ->
            if (dataset == null) {
                Snackbar.make(binding.root, "Carga Los Datos", Snackbar.LENGTH_SHORT).show()
            } else {
                setupRecyclerView(dataset)
                vectorDatasetViewModel.sortArrays(dataset)
            }
        }
        vectorDatasetViewModel.getSortedDatasetResult().observe(viewLifecycleOwner) { sortedData ->
            if (sortedData == null) {
                Snackbar.make(binding.root, "Carga Los Datos", Snackbar.LENGTH_SHORT).show()
            } else {

            }
        }
    }

    private fun configureButtons() {
        binding.btnChargeData.setOnClickListener {
            selectCSVFile()
        }
    }

    private fun setupRecyclerView(listMultiLines: MutableList<MutableList<Double>>) {
        val operationsWithArrays = OperationsWithArrays()
        val matriz = operationsWithArrays.printArray(listMultiLines)
        binding.tvMatriz.text = matriz
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
                    vectorDatasetViewModel.readTextFromUri(uri, requireContext())
                    //sortArrays()
                }
            }
        }
    }

}