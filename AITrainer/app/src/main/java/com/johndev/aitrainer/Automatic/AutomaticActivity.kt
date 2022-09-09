package com.johndev.aitrainer.Automatic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.aitrainer.Automatic.AutomaticCalculoModule.view.AutomaticCalculoFragment
import com.johndev.aitrainer.Automatic.AutomaticCalculoModule.viewModel.AutomaticViewModel
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.view.AutomaticChargeDatasetsFragment
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.viewModel.AutomaticChargeDataViewModel
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.view.AutomaticPrintDatasetFragment
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.viewModel.AutomaticPrintViewModel
import com.johndev.aitrainer.common.adapters.ViewPagerAdapter
import com.johndev.aitrainer.databinding.ActivityAutomaticBinding

class AutomaticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAutomaticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutomaticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        configureViewPager()
        configureToolbar()
    }

    private fun configureToolbar() {
        binding.toolbar.title = "Automatic"
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun configureViewPager() {
        val onboardingAdapter = ViewPagerAdapter(supportFragmentManager)
        onboardingAdapter.add(AutomaticChargeDatasetsFragment(), "Charge Data")
        onboardingAdapter.add(AutomaticCalculoFragment(), "Operations")
        onboardingAdapter.add(AutomaticChartFragment(), "Chart")
        onboardingAdapter.add(AutomaticPrintDatasetFragment(), "Print")
        binding.viewPager.adapter = onboardingAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewModel() {
        automaticChargeDataViewModel = ViewModelProvider(this)[AutomaticChargeDataViewModel::class.java]
        automaticOpViewModel = ViewModelProvider(this)[AutomaticViewModel::class.java]
        automaticPrintViewModel = ViewModelProvider(this)[AutomaticPrintViewModel::class.java]
    }

    companion object {
        lateinit var automaticChargeDataViewModel: AutomaticChargeDataViewModel
        lateinit var automaticOpViewModel: AutomaticViewModel
        lateinit var automaticPrintViewModel: AutomaticPrintViewModel
    }

}