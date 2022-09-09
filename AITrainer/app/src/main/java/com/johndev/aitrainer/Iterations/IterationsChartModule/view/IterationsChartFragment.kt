package com.johndev.aitrainer.Iterations.IterationsChartModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.johndev.aitrainer.Iterations.IterationsMethodActivity.Companion.iterationsViewModel
import com.johndev.aitrainer.databinding.FragmentIterationsChartBinding

class IterationsChartFragment : Fragment() {

    private var _binding: FragmentIterationsChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIterationsChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        iterationsViewModel.getResultOperations().observe(viewLifecycleOwner) { results ->
            if (results == null) {
                Toast.makeText(context, "Regrese a realizar los calculos", Toast.LENGTH_SHORT).show()
            } else {
                //sortChartData(500, results)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}