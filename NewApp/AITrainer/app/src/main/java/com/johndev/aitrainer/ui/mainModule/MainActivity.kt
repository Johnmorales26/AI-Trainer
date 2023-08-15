package com.johndev.aitrainer.ui.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.adapters.AdapterMainMenu
import com.johndev.aitrainer.common.entities.MainMenuEntity
import com.johndev.aitrainer.databinding.ActivityMainBinding
import com.johndev.aitrainer.interfaces.OnMainMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}