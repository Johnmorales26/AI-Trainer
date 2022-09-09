package com.johndev.aitrainer.Iterations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.view.ChargeDatasetsFragment
import com.johndev.aitrainer.Iterations.IterationsMethodModule.view.IterationsOperationsFragment
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.viewModel.ChargeDataViewModel
import com.johndev.aitrainer.Iterations.IterationsMethodModule.viewModel.IterationsViewModel
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.view.PrintDatasetFragment
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.viewModel.PrintViewModel
import com.johndev.aitrainer.common.adapters.ViewPagerAdapter
import com.johndev.aitrainer.databinding.ActivityIterationsMethodBinding

class IterationsMethodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIterationsMethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIterationsMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        configureViewPager()
    }

    private fun configureViewPager() {
        val onboardingAdapter = ViewPagerAdapter(supportFragmentManager)
        onboardingAdapter.add(ChargeDatasetsFragment(), "Charge Data")
        onboardingAdapter.add(IterationsOperationsFragment(), "Operations")
        onboardingAdapter.add(IterationsOperationsFragment(), "Chart")
        onboardingAdapter.add(PrintDatasetFragment(), "Print")
        binding.viewPager.adapter = onboardingAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewModel() {
        chargeDataViewModel = ViewModelProvider(this)[ChargeDataViewModel::class.java]
        iterationsViewModel = ViewModelProvider(this)[IterationsViewModel::class.java]
        printDataViewModel = ViewModelProvider(this)[PrintViewModel::class.java]
    }

    companion object {
        lateinit var chargeDataViewModel: ChargeDataViewModel
        lateinit var iterationsViewModel: IterationsViewModel
        lateinit var printDataViewModel: PrintViewModel
    }

}