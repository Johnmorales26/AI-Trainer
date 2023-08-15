package com.johndev.aitrainer.ui.manualRegressionModule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johndev.aitrainer.R
import com.johndev.aitrainer.adapters.ViewPagerAdapter
import com.johndev.aitrainer.databinding.FragmentManualRegressionBinding

class ManualRegressionFragment : Fragment() {

    private var _binding: FragmentManualRegressionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManualRegressionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listViews = listOf(
            Pair(LoadDataFragment(), getString(R.string.title_data_upload)),
            Pair(OperationManualFragment(), getString(R.string.title_training)),
            Pair(ChartFragment(), getString(R.string.title_chart)),
            Pair(PrintResultFragment(), getString(R.string.title_print))
        )
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, listViews)
        binding.apply {
            pager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(binding.pager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}