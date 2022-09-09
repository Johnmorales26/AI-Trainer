package com.johndev.aitrainer.Vectors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.aitrainer.Vectors.VectorChargeDataModule.view.VectorDatasetsFragment
import com.johndev.aitrainer.Vectors.VectorChargeDataModule.viewModel.VectorChargeDataViewModel
import com.johndev.aitrainer.Vectors.VectorOperationsModule.view.VectorOperationsFragment
import com.johndev.aitrainer.Vectors.VectorOperationsModule.viewModel.VectorOperationsViewModel
import com.johndev.aitrainer.Vectors.VectorPrintDataModule.view.VectorPrintFragment
import com.johndev.aitrainer.Vectors.VectorPrintDataModule.viewModel.VectorPrintViewModel
import com.johndev.aitrainer.common.adapters.ViewPagerAdapter
import com.johndev.aitrainer.databinding.ActivityVectorsBinding

class VectorsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVectorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVectorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        configureViewPager()
    }

    private fun setupViewModel() {
        vectorDatasetViewModel = ViewModelProvider(this)[VectorChargeDataViewModel::class.java]
        vectorOperationsViewModel = ViewModelProvider(this)[VectorOperationsViewModel::class.java]
        vectorPrintViewModel = ViewModelProvider(this)[VectorPrintViewModel::class.java]
    }

    private fun configureViewPager() {
        val onboardingAdapter = ViewPagerAdapter(supportFragmentManager).apply {
            add(VectorDatasetsFragment(), "Charge Data")
            add(VectorOperationsFragment(), "Operations")
            add(VectorChartsFragment(), "Chart")
            add(VectorPrintFragment(), "Print")
            //onboardingAdapter.add(VectorChartsFragment(), "Chart")
            //onboardingAdapter.add(VectorPrintFragment(), "Print")
        }
        binding.viewPager.adapter = onboardingAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    companion object {
        lateinit var vectorDatasetViewModel: VectorChargeDataViewModel
        lateinit var vectorOperationsViewModel: VectorOperationsViewModel
        lateinit var vectorPrintViewModel: VectorPrintViewModel
    }

}