package com.johndev.aitrainer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.johndev.aitrainer.ExtraFragments.SettingsActivity
import com.johndev.aitrainer.Models.Automatic
import com.johndev.aitrainer.VectorizedImplementarion.VectorFragment
import com.johndev.aitrainer.databinding.ActivityMainBinding
import com.johndev.aitrainer.regresion_automatic.AutomaticRegresionFragment
import com.johndev.aitrainer.regresion_manual.ManualRegresionFragment
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        printAutomatic = mutableListOf()
        setupIntroduction()
        setupActionBar()
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayout.close()
            when (menuItem.itemId) {
                R.id.action_manual_regression -> {
                    with(binding) {
                        mainIntroduction.visibility = GONE
                        mainContainer.visibility = VISIBLE
                        topAppBar.title = getString(R.string.manual_regression)
                    }
                    val fragment = ManualRegresionFragment()
                    openFragment(fragment)
                }
                R.id.action_automatic_regresion -> {
                    with(binding) {
                        mainIntroduction.visibility = GONE
                        mainContainer.visibility = VISIBLE
                        topAppBar.title = getString(R.string.automatic_regression)
                    }
                    val fragment = AutomaticRegresionFragment()
                    openFragment(fragment)
                }
                R.id.action_vector_regresion -> {
                    with(binding) {
                        mainIntroduction.visibility = GONE
                        mainContainer.visibility = VISIBLE
                        topAppBar.title = getString(R.string.vector_regression)
                    }
                    val fragment = VectorFragment()
                    openFragment(fragment)
                }
                R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            true
        }
    }

    private fun setupSound() {
        directionSound = when (sharedPreferences.getString(getString(R.string.key_preference_application_sound), "")) {
            getString(R.string.preference_key_sound_one) -> R.raw.task_complete_one
            getString(R.string.preference_key_sound_two) -> R.raw.task_complete_two
            getString(R.string.preference_key_sound_three) -> R.raw.task_complete_three
            getString(R.string.preference_key_sound_four) -> R.raw.task_complete_four
            getString(R.string.preference_key_sound_five) -> R.raw.task_complete_five
            getString(R.string.preference_key_sound_six) -> R.raw.task_complete_six
            else -> R.raw.task_complete_one
        }
    }

    override fun onResume() {
        super.onResume()
        setupTheme()
        setupLanguaje()
        setupSound()
    }

    private fun setupLanguaje() {
        when (sharedPreferences.getString(getString(R.string.key_preference_application_languaje), "")) {
            getString(R.string.preference_languaje_english) -> {
                Toast.makeText(this, "La aplicacion esta en ingles", Toast.LENGTH_SHORT).show()
            }
            getString(R.string.preference_languaje_spanish) -> {
                Toast.makeText(this, "La aplicacion esta en español", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTheme() {
        when (sharedPreferences.getBoolean(getString(R.string.key_preference_enable_light_mode), true)) {
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupIntroduction() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.main_introduction, IntroductionFragment())
            addToBackStack(null)
            commit()
        }

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.main_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding) {
            binding.topAppBar.title = getString(R.string.app_name)
            binding.topAppBar.setNavigationOnClickListener {
                /*var modalFragment = ModalNavigationDrawerFragment()
                modalFragment.show(supportFragmentManager.beginTransaction(), ModalNavigationDrawerFragment.TAG)*/
            }
        }
    }

    companion object{
        lateinit var appContext: Context
        lateinit var printAutomatic: MutableList<Automatic>
        lateinit var sharedPreferences: SharedPreferences
        var directionSound by Delegates.notNull<Int>()
    }

}