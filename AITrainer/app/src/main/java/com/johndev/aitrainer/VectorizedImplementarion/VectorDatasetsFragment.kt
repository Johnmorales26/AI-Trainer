package com.johndev.aitrainer.VectorizedImplementarion

import OperationsWithArrays
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.adapters.VectorDatasetsAdapter
import com.johndev.aitrainer.databinding.FragmentVectorDatasetsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

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
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnChargeData.setOnClickListener {
            selectCSVFile()
        }
    }

    suspend fun sortArrays() = withContext(Dispatchers.IO) {
        var arrayX = Array(listMultiLines.size) { DoubleArray(listMultiLines[0].size - 1) }
        var arrayY = Array(listMultiLines.size) { DoubleArray(1) }
        var arrayW = Array(arrayX[0].size) { DoubleArray(1) }

        //  Crear copias del dataset
        val datasetX: MutableList<MutableList<Double>> = mutableListOf()
        listMultiLines.forEach {
            datasetX.add(it)
        }
        //  Export Array Y
        var indexArray = 0
        datasetX.forEach {
            var index  = it.size - 1
            var listY: MutableList<Double> = mutableListOf()
            listY.add(it[index])
            arrayY[indexArray] = listY.toDoubleArray()
            indexArray++
        }
        //  Export Array X
        indexArray = 0
        datasetX.forEach {
            val x: MutableList<Double> = mutableListOf()
            it.removeLast()
            it.forEach {
                x.add(it)
            }
            arrayX[indexArray] = x.toDoubleArray()
            indexArray++
        }
        indexArray = 0
        arrayW.forEach {
            it.forEach {
                val listW: MutableList<Double> = mutableListOf(0.0)
                arrayW[indexArray] = listW.toDoubleArray()
            }
            indexArray++
        }
        sendArrayX = arrayX
        sendArrayY = arrayY
        sendArrayW = arrayW
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
                    readTextFromUri(uri)
                    setupRecyclerView(listMultiLines)
                    sortArrays()
                }
            }
        }
    }

    private suspend fun readTextFromUri(uri: Uri) = withContext(Dispatchers.IO) {
        requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                var i = 0
                val counterValues = 0
                listMultiLines = mutableListOf()
                while (line != null) {
                    val lineDataset: MutableList<Double> = mutableListOf()
                    val textElements = line.toString().split(",")
                    textElements.forEach {
                        lineDataset.add(it.trim().toDouble())
                    }
                    listMultiLines.add(lineDataset)
                    i++
                    line = reader.readLine()
                }
            }
        }
    }

    companion object {
        private lateinit var listMultiLines: MutableList<MutableList<Double>>
        lateinit var sendArrayX: Array<DoubleArray>
        lateinit var sendArrayY: Array<DoubleArray>
        lateinit var sendArrayW: Array<DoubleArray>
    }

}