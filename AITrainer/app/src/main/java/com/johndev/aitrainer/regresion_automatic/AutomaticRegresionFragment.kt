package com.johndev.aitrainer.regresion_automatic

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.ChargeDatasetsFragment
import com.johndev.aitrainer.common.ChartFragment
import com.johndev.aitrainer.databinding.FragmentAutomaticRegresionBinding

class AutomaticRegresionFragment : Fragment() {

    private var _binding: FragmentAutomaticRegresionBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticRegresionBinding.inflate(inflater, container, false)
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
                    fragment = AutomaticCalculoFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_chart -> {
                    fragment = ChartFragment()
                    openFragment(fragment)
                    true
                }
                R.id.action_manual_print_data -> {
                    fragment = AutomaticPrintFragment()
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