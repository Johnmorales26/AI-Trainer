package com.johndev.aitrainer.VectorizedImplementarion

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.johndev.aitrainer.BottomOptions.BottomOptionsFragment
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.Models.ResultsVector
import com.johndev.aitrainer.R
import com.johndev.aitrainer.VectorizedImplementarion.VectorOperationsFragment.Companion.chartVectorData
import com.johndev.aitrainer.databinding.FragmentAutomaticPrintBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class VectorPrintFragment : Fragment() {

    private var _binding: FragmentAutomaticPrintBinding? = null
    private val binding get() = _binding!!
    private val CODE_PERMISSION_STORAGE = 3
    private lateinit var nameFile: String
    private lateinit var extensionFile: String
    private val separatorCSV = ","

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticPrintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnSave.setOnClickListener {
            if (validFields()){
                Snackbar.make(binding.root, "Creando Archivo", Snackbar.LENGTH_LONG).show()
                nameFile = binding.etNameFile.text.toString().trim()
                extensionFile = binding.btnExtension.text.toString().trim()
                checkStoragePermission()
                sortAnswer()
            }
        }
        binding.btnExtension.setOnClickListener {
            val fragment = BottomOptionsFragment(
                onSubmitClickListener = { data ->
                    binding.btnExtension.text = data.name.trim()
                }
            )
            val sendData = Bundle()
            sendData.putString("option", "Extension")
            fragment.arguments = sendData
            fragmentManager?.let { it1 -> fragment.show(it1.beginTransaction(), BottomOptionsFragment::class.java.simpleName) }
        }
    }

    private fun sortAnswer() {
        val resultList: MutableList<ResultsVector> = ArrayList()
        var i = 0
        while (i < chartVectorData.size){
            resultList.add(
                ResultsVector(
                    chartVectorData[i].epoch, chartVectorData[i].arrayW, chartVectorData[i].valueJ)
            )
            i++
        }
        when (binding.btnExtension.text) {
            getString(R.string.file_extension_json) -> {
                lifecycleScope.launch {
                    val exportJSON = createJSON(resultList)
                    writeToFile(exportJSON)
                }
            }
            getString(R.string.file_extension_csv) -> {
                lifecycleScope.launch {
                    val exportCSV = createCSV(resultList)
                    writeToFile(exportCSV)
                }
            }
            getString(R.string.file_extension_txt) -> {
                lifecycleScope.launch {
                    val exportTXT = createTXT(resultList)
                    writeToFile(exportTXT)
                }
            }
        }
    }

    private suspend fun createJSON(resultList: MutableList<ResultsVector>): String = withContext(
        Dispatchers.IO) {
        val gson = Gson()
        val exportJSON = gson.toJson(resultList)
        Log.i("JSON EXPORT", exportJSON.toString())
        return@withContext exportJSON
    }

    private suspend fun createTXT(resultList: MutableList<ResultsVector>): String = withContext(
        Dispatchers.IO) {
        var exportTXT = ""
        resultList.forEach {
            var index = 0
            var Ws = ""
            it.ListWs.forEach { arrayW ->
                arrayW.forEach { valueW ->
                    Ws += "-- W$index: $valueW --"
                    index++
                }
            }
            exportTXT += "Epoch: ${it.ID} $Ws J: ${it.J}\n"
        }
        return@withContext exportTXT
    }

    private suspend fun createCSV(resultList: MutableList<ResultsVector>): String = withContext(
        Dispatchers.IO) {
        var exportCSV = ""
        resultList.forEach {
            var index = 0
            var Ws = ""
            it.ListWs.forEach { arrayW ->
                arrayW.forEach { valueW ->
                    Ws += "$separatorCSV$valueW"
                    index++
                }
            }
            exportCSV += "${it.ID}${Ws}$separatorCSV${it.J}\n"
        }
        return@withContext exportCSV
    }

    private suspend fun writeToFile(exportFile: String?) = withContext(Dispatchers.IO) {
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, "$nameFile$extensionFile".trim())
        if (!file.exists()){
            file.createNewFile()
        }
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.apply {
            write(exportFile)
            close()
        }
        val sound = MainActivity.sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            MediaPlayer.create(context, MainActivity.directionSound).start()
        }
        Snackbar.make(binding.root, "Archivo Creado", Snackbar.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty()){
            return
        }
        if (requestCode == CODE_PERMISSION_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //  Aqui se ha concedido el permiso
            } else {
                Toast.makeText(context, "Permiso de almacenamiento requerido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkStoragePermission(){
        val permissionState = context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if (permissionState == PackageManager.PERMISSION_GRANTED){
            //  Aqui se ha concedido el permiso
        } else {
            //  Si no, pedimos permisos.
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    CODE_PERMISSION_STORAGE
                )
            }
        }
    }

    private fun validFields(): Boolean {
        var isValid = true
        //  Evaluando value A
        if (binding.etNameFile.text.isNullOrEmpty()) {
            binding.tilNameFile.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilNameFile.error = null
        }
        return isValid
    }

}