package com.johndev.aitrainer.Iterations.IterationsPrintDataModule.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.johndev.aitrainer.BottomOptions.BottomOptionsFragment
import com.johndev.aitrainer.common.entities.ResultsJSON
import com.johndev.aitrainer.R
import com.johndev.aitrainer.Ranges.RangesMethodActivity.Companion.rangesPrintDataViewModel
import com.johndev.aitrainer.Ranges.RangesMethodActivity.Companion.rangesViewModel
import com.johndev.aitrainer.databinding.FragmentPrintDatasetBinding

class RangesPrintDatasetFragment : Fragment() {

    private var _binding: FragmentPrintDatasetBinding? = null
    private val binding get() = _binding!!
    private val CODE_PERMISSION_STORAGE = 3
    private lateinit var nameFile: String
    private lateinit var extensionFile: String
    private val resultList: MutableList<ResultsJSON> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrintDatasetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        configureButtons()
    }

    private fun setupObservers() {
        rangesPrintDataViewModel.getResultSaved().observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == null) {
                Toast.makeText(context, "Imprima sus datos", Toast.LENGTH_SHORT).show()
            } else {
                when(isSuccess) {
                    true -> { Toast.makeText(context, "Exportación Exitosa", Toast.LENGTH_SHORT).show() }
                    false -> { Toast.makeText(context, "Exportación Fallida", Toast.LENGTH_SHORT).show() }
                }
            }
        }
        rangesViewModel.getResultOperations().observe(viewLifecycleOwner) { result ->
            if (result == null) {
                Toast.makeText(context, "Realize los calculos", Toast.LENGTH_SHORT).show()
            } else {
                var i = 0
                while (i < result.size){
                    resultList.add(ResultsJSON(result[i].id, result[i].valueW, result[i].valueJW))
                    i++
                }
            }
        }
    }

    private fun configureButtons() {
        binding.btnSave.setOnClickListener {
            if (validFields()){
                nameFile = binding.etNameFile.text.toString().trim()
                extensionFile = binding.btnExtension.text.toString().trim()
                checkStoragePermission()
                configureExport(nameFile, extensionFile, resultList)
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

    private fun configureExport(nameFile: String, extensionFile: String, resultJson: MutableList<ResultsJSON>) {
        when (extensionFile) {
            getString(R.string.file_extension_json) -> { rangesPrintDataViewModel.createJSON(nameFile, extensionFile, resultJson) }
            getString(R.string.file_extension_txt) -> { rangesPrintDataViewModel.createTXT(nameFile, extensionFile, resultJson) }
            getString(R.string.file_extension_csv) -> { rangesPrintDataViewModel.createCSV(nameFile, extensionFile, resultJson) }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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