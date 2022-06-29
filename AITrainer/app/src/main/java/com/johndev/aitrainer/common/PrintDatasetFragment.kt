package com.johndev.aitrainer.common

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.johndev.aitrainer.BottomOptions.BottomOptionsFragment
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.Models.ResultsJSON
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.FragmentPrintDatasetBinding
import com.johndev.aitrainer.regresion_manual.ManualCalculoFragment.Companion.resultsPerceptron
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class PrintDatasetFragment : Fragment() {

    private var _binding: FragmentPrintDatasetBinding? = null
    private val binding get() = _binding!!
    private val CODE_PERMISSION_STORAGE = 3
    private lateinit var nameFile: String
    private lateinit var extensionFile: String
    private val separatorCSV = ","

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrintDatasetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnSave.setOnClickListener {
            if (validFields()){
                nameFile = binding.etNameFile.text.toString().trim()
                extensionFile = binding.btnExtension.text.toString().trim()
                checkStoragePermission()
                ordenateAnswer()
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

    private fun ordenateAnswer() {
        val resultList: MutableList<ResultsJSON> = ArrayList()
        var i = 0
        while (i < resultsPerceptron.size){
            resultList.add(ResultsJSON(resultsPerceptron[i].id, resultsPerceptron[i].valueW, resultsPerceptron[i].valueJW))
            i++
        }
        when (binding.btnExtension.text) {
            getString(R.string.file_extension_json) -> { createJSON(resultList) }
            getString(R.string.file_extension_txt) -> { createTXT(resultList) }
            getString(R.string.file_extension_csv) -> { createCSV(resultList) }
        }
    }

    private fun createJSON(resultList: MutableList<ResultsJSON>) {
        val gson = Gson()
        val exportJSON = gson.toJson(resultList)
        Log.i("JSON EXPORT", exportJSON.toString())
        writeToFile(exportJSON)
    }

    private fun createTXT(resultList: MutableList<ResultsJSON>) {
        var exportTXT = ""
        resultList.forEach {
            exportTXT += "Iteraciones: ${it.id} ---------- W: ${it.w} ---------- JW: ${it.jw}\n"
        }
        writeToFile(exportTXT)
    }

    private fun createCSV(resultList: MutableList<ResultsJSON>) {
        var exportCSV = ""
        resultList.forEach {
            exportCSV += "${it.id}$separatorCSV${it.w}$separatorCSV${it.jw}\n"
        }
        Toast.makeText(context, exportCSV, Toast.LENGTH_LONG).show()
        writeToFile(exportCSV)
    }

    private fun writeToFile(exportFile: String?) {
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Toast.makeText(context, "Guardando en $filePath", Toast.LENGTH_LONG).show()
        val file = File(filePath, "$nameFile$extensionFile".trim())
        if (!file.exists()){
            file.createNewFile()
        }
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(exportFile)
        bufferedWriter.close()
        val sound = MainActivity.sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            val mediaPlayer = MediaPlayer.create(context, R.raw.task_complete_six)
            mediaPlayer.start()
        }
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
            Toast.makeText(context, "Permisos Concedidos", Toast.LENGTH_SHORT).show()
        } else {
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