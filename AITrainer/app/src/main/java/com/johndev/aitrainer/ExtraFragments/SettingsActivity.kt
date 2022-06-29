package com.johndev.aitrainer.ExtraFragments

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_introduction, SettingsFragment())
            .commit()
    }

    private fun setupToolbar() {
        binding.topAppBar.title = getString(R.string.settings_title)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}