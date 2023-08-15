package com.johndev.aitrainer.ui.homeModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.adapters.AdapterMainMenu
import com.johndev.aitrainer.common.entities.MainMenuEntity
import com.johndev.aitrainer.databinding.FragmentHomeBinding
import com.johndev.aitrainer.interfaces.OnMainMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMainMenu {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: homeViewModel
    private lateinit var adapter: AdapterMainMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupRecyclerView()
    }

    private fun initViewModel() {
        val vmMain: homeViewModel by viewModels()
        viewModel = vmMain
    }

    private fun setupRecyclerView() {
        adapter = AdapterMainMenu(requireContext(), this)
        adapter.submitList(viewModel.getMenuItems())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeFragment.adapter
        }
    }

    override fun onClick(mainMenu: MainMenuEntity) {
        when(mainMenu.id) {
            1 -> navigation(HomeFragmentDirections.actionHomeToNavigationManualRegression())
            2 -> navigation(HomeFragmentDirections.actionHomeToNavigationRegression())
            3 -> navigation(HomeFragmentDirections.actionHomeToNavigationVectorRegression())
        }

    }

    private fun navigation(action: NavDirections) {
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}