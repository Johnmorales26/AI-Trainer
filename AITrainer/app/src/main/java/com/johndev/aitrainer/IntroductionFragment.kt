package com.johndev.aitrainer

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.IntroductionCardsAdapter
import com.johndev.aitrainer.Interfaces.OnIntroductionListener
import com.johndev.aitrainer.Models.Introduction
import com.johndev.aitrainer.databinding.FragmentAutomaticCalculoBinding
import com.johndev.aitrainer.databinding.FragmentIntroductionBinding


class IntroductionFragment : Fragment(), OnIntroductionListener {

    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!
    private lateinit var introductionCardsAdapter: IntroductionCardsAdapter
    private var urlImage = "android.resource://com.johndev.aitrainer/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
    }

    private fun configRecyclerView() {
        introductionCardsAdapter = IntroductionCardsAdapter(loadValues(), this)
        binding.recyclerView.apply {
            //layoutManager = LinearLayoutManager(context)
            layoutManager = LinearLayoutManager(context)
            adapter = introductionCardsAdapter
        }
    }

    private fun loadValues(): MutableList<Introduction> {
        return mutableListOf(
            Introduction(1, getString(R.string.introduction_title_what_is_ia), getString(R.string.introduction_descriptio_what_is_ia), Uri.parse(urlImage + R.drawable.img_artificial_intellicense)),
            Introduction(2, getString(R.string.introduction_title_data), getString(R.string.introduction_descriptio_data), Uri.parse(urlImage + R.drawable.img_data)),
            Introduction(2, getString(R.string.introduction_title_training), getString(R.string.introduction_descriptio_training), Uri.parse(urlImage + R.drawable.img_training)),
            Introduction(2, getString(R.string.introduction_title_overfitting), getString(R.string.introduction_descriptio_overfitting), Uri.parse(urlImage + R.drawable.img_overfittiing)),
            Introduction(2, getString(R.string.introduction_title_stores), getString(R.string.introduction_descriptio_strores), Uri.parse(urlImage + R.drawable.img_stores)),
            Introduction(2, getString(R.string.introduction_title_filter), getString(R.string.introduction_descriptio_filter), Uri.parse(urlImage + R.drawable.imf_filter))
        )
    }

}