package com.johndev.aitrainer.VectorizedImplementarion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.ChargeDatasetsFragment
import com.johndev.aitrainer.common.ChartFragment
import com.johndev.aitrainer.databinding.FragmentAutomaticRegresionBinding
import com.johndev.aitrainer.databinding.FragmentVectorBinding
import com.johndev.aitrainer.regresion_automatic.AutomaticCalculoFragment
import com.johndev.aitrainer.regresion_automatic.AutomaticPrintFragment

class VectorFragment : Fragment() {

    private var _binding: FragmentVectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_charge_data -> {
                    fragment = VectorDatasetsFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_operations -> {
                    fragment = VectorOperationsFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_chart -> {
                    fragment = VectorChartsFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_print_data -> {
                    fragment = VectorPrintFragment()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.action_charge_data
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.main_manual_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}