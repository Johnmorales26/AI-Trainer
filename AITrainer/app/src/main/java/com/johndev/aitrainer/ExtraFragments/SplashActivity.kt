package com.johndev.aitrainer.ExtraFragments

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var handler = Handler()
    private var isActive = false
    private var value = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setupTheme()
        loadProgressBar()
    }

    private fun setupTheme() {
        when (sharedPreferences.getBoolean(getString(R.string.key_preference_enable_light_mode), true)) {
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun loadProgressBar() {
        var progress = 0
        if (!isActive) {
            var thread = Thread(Runnable {
                kotlin.run {
                    while (progress <= 100) {
                        handler.post(Runnable {
                            kotlin.run {
                                binding.tvChargePercent.text = "$progress%"
                                binding.progressBar.progress = progress
                            }
                        })
                        try {
                            Thread.sleep(15)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        if (progress == 100) {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        progress++
                        isActive = true
                    }
                }
            })
            thread.start()
        }
    }

}