package com.johndev.aitrainer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.johndev.aitrainer.Automatic.AutomaticActivity
import com.johndev.aitrainer.Iterations.IterationsMethodActivity
import com.johndev.aitrainer.ExtraFragments.SettingsActivity
import com.johndev.aitrainer.common.entities.Automatic
import com.johndev.aitrainer.databinding.ActivityMainBinding
import com.johndev.aitrainer.Ranges.RangesMethodActivity
import com.johndev.aitrainer.Vectors.VectorsActivity

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
                R.id.action_manual_regression_iterations -> { startActivity(Intent(this, IterationsMethodActivity::class.java)) }
                R.id.action_automatic_regresion_ranges -> { startActivity(Intent(this, RangesMethodActivity::class.java)) }
                R.id.action_automatic_regresion -> { startActivity(Intent(this, AutomaticActivity::class.java)) }
                R.id.action_vector_regresion -> { startActivity(Intent(this, VectorsActivity::class.java)) }
                R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            true
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
        lateinit var printAutomatic: MutableList<Automatic>
        lateinit var sharedPreferences: SharedPreferences
    }

}