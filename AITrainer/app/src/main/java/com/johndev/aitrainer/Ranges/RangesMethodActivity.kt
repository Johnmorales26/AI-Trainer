package com.johndev.aitrainer.Ranges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.view.RangesChargeDatasetsFragment
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.viewModel.RangesChargeDataViewModel
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.view.RangesPrintDatasetFragment
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.viewModel.RangesPrintViewModel
import com.johndev.aitrainer.Ranges.RangesMethodModule.view.RangesOperationsFragment
import com.johndev.aitrainer.Ranges.RangesMethodModule.viewModel.RangesViewModel
import com.johndev.aitrainer.common.adapters.ViewPagerAdapter
import com.johndev.aitrainer.databinding.ActivityRangesMethodBinding

class RangesMethodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRangesMethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangesMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        configureViewPager()
    }

    private fun setupViewModel() {
        rangesChargeDataViewModel = ViewModelProvider(this)[RangesChargeDataViewModel::class.java]
        rangesViewModel = ViewModelProvider(this)[RangesViewModel::class.java]
        rangesPrintDataViewModel = ViewModelProvider(this)[RangesPrintViewModel::class.java]
    }

    private fun configureViewPager() {
        val onboardingAdapter = ViewPagerAdapter(supportFragmentManager).apply {
            add(RangesChargeDatasetsFragment(), "Charge Data")
            add(RangesOperationsFragment(), "Operations")
            //onboardingAdapter.add(IterationsOperationsFragment(), "Chart")
            add(RangesPrintDatasetFragment(), "Print")
        }
        binding.viewPager.adapter = onboardingAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    companion object {
        lateinit var rangesChargeDataViewModel: RangesChargeDataViewModel
        lateinit var rangesViewModel: RangesViewModel
        lateinit var rangesPrintDataViewModel: RangesPrintViewModel
    }

}