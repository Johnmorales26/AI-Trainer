package com.johndev.aitrainer.regresion_manual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.ChargeDatasetsFragment
import com.johndev.aitrainer.common.ChartFragment
import com.johndev.aitrainer.common.PrintDatasetFragment
import com.johndev.aitrainer.databinding.FragmentManualRegresionBinding


class ManualRegresionFragment : Fragment() {

    private var _binding: FragmentManualRegresionBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManualRegresionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_charge_data -> {
                    fragment = ChargeDatasetsFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_operations -> {
                    fragment = ManualCalculoFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_chart -> {
                    fragment = ChartFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_print_data -> {
                    fragment = PrintDatasetFragment()
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