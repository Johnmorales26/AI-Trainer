package com.johndev.aitrainer.Vectors.VectorPrintDataModule.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.johndev.aitrainer.BottomOptions.BottomOptionsFragment
import com.johndev.aitrainer.common.entities.ResultsVector
import com.johndev.aitrainer.R
import com.johndev.aitrainer.Vectors.VectorsActivity.Companion.vectorOperationsViewModel
import com.johndev.aitrainer.Vectors.VectorsActivity.Companion.vectorPrintViewModel
import com.johndev.aitrainer.databinding.FragmentAutomaticPrintBinding
import kotlinx.coroutines.launch

class VectorPrintFragment : Fragment() {

    private var _binding: FragmentAutomaticPrintBinding? = null
    private val binding get() = _binding!!
    private val CODE_PERMISSION_STORAGE = 3
    private lateinit var nameFile: String
    private lateinit var extensionFile: String
    private val resultList: MutableList<ResultsVector> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticPrintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        configureButtons()
    }

    private fun setupObservers() {
        vectorOperationsViewModel.getResultOperations().observe(viewLifecycleOwner) { result ->
            if (result == null) {
                Toast.makeText(context, "Realiza los calculos", Toast.LENGTH_SHORT).show()
            } else {
                var i = 0
                while (i < result.paintResults.size) {
                    resultList.add(
                        ResultsVector(
                            result.paintResults[i].index, result.wResults, result.paintResults[i].J
                        )
                    )
                    i++
                }
            }
        }
        vectorPrintViewModel.getResultSaved().observe(viewLifecycleOwner) { isExported ->
            if (isExported == null || isExported == false) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureButtons() {
        binding.btnSave.setOnClickListener {
            if (validFields()) {
                Snackbar.make(binding.root, "Creando Archivo", Snackbar.LENGTH_LONG).show()
                nameFile = binding.etNameFile.text.toString().trim()
                extensionFile = binding.btnExtension.text.toString().trim()
                checkStoragePermission()
                exportData()
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
            fragmentManager?.let { it1 ->
                fragment.show(
                    it1.beginTransaction(),
                    BottomOptionsFragment::class.java.simpleName
                )
            }
        }
    }

    private fun exportData() {
        when (binding.btnExtension.text) {
            getString(R.string.file_extension_json) -> {
                lifecycleScope.launch {
                    vectorPrintViewModel.createJSON(nameFile, extensionFile, resultList)
                    //val exportJSON = createJSON(resultList)
                    //writeToFile(exportJSON)
                }
            }
            getString(R.string.file_extension_csv) -> {
                lifecycleScope.launch {
                    vectorPrintViewModel.createCSV(nameFile, extensionFile, resultList)
                    //val exportCSV = createCSV(resultList)
                    //writeToFile(exportCSV)
                }
            }
            getString(R.string.file_extension_txt) -> {
                lifecycleScope.launch {
                    vectorPrintViewModel.createTXT(nameFile, extensionFile, resultList)
                    //val exportTXT = createTXT(resultList)
                    //writeToFile(exportTXT)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty()) {
            return
        }
        if (requestCode == CODE_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Aqui se ha concedido el permiso
            } else {
                Toast.makeText(context, "Permiso de almacenamiento requerido", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkStoragePermission() {
        val permissionState = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
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