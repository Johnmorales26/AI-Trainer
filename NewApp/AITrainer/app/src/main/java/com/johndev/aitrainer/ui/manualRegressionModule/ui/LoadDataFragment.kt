package com.johndev.aitrainer.ui.manualRegressionModule.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.johndev.aitrainer.common.utils.Constans.PICK_CSV_FILE
import com.johndev.aitrainer.databinding.ViewLoadDataBinding
import com.johndev.aitrainer.ui.manualRegressionModule.ManualRegressionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadDataFragment : Fragment()  {

    private var _binding: ViewLoadDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ManualRegressionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewLoadDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupObservers()
        setupButtons()
    }

    private fun setupObservers() {
        viewModel.dataset.observe(viewLifecycleOwner) {
            it?.let { dataset ->
                var textPrint = ""
                dataset.forEach {
                    textPrint += "$it\n"
                    binding.tvDataset.text = textPrint
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btnSelectFile.setOnClickListener { selectCSVFile() }
    }

    private fun initViewModel() {
        val vmMain: ManualRegressionViewModel by viewModels()
        viewModel = vmMain
    }

    private fun selectCSVFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), PICK_CSV_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == PICK_CSV_FILE
            && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                lifecycleScope.launch {
                    viewModel.readTextFromUri(uri)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}