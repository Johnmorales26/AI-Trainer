package com.johndev.aitrainer.common

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.DatasetAdapter
import com.johndev.aitrainer.Interfaces.OnDatasetListener
import com.johndev.aitrainer.MainActivity.Companion.directionSound
import com.johndev.aitrainer.Models.Dataset
import com.johndev.aitrainer.R
import com.johndev.aitrainer.MainActivity.Companion.sharedPreferences
import com.johndev.aitrainer.databinding.FragmentChargeDatasetsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class ChargeDatasetsFragment : Fragment(), OnDatasetListener {

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

    private suspend fun readTextFromUri(uri: Uri) = withContext(Dispatchers.IO) {
        requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                var i = 0
                while (line != null) {
                    val textElements = line.toString().split(",")
                    val valueX = textElements[0].trim().toDouble()
                    val valueY = textElements[1].trim().toDouble()
                    dataset.add(Dataset(valueX, valueY))
                    valuesX.add(valueX)
                    valuesY.add(valueY)
                    println("Vuelta: $i------${dataset[i].dataX}, ${dataset[i].dataY}")
                    i++
                    line = reader.readLine()
                }
            }
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
                    readTextFromUri(uri)
                    setupRecyclerView(dataset)
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
        val sound = sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            MediaPlayer.create(context, directionSound).start()
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