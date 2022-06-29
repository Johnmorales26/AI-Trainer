package com.johndev.aitrainer.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemResultsXBinding
import com.johndev.aitrainer.format.NumberFormat

class PrintDataVectoresAdapter(private val arrayUpdateW: Array<DoubleArray>, private val typeDecimal: String):
    RecyclerView.Adapter<PrintDataVectoresAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var sharedPreferences: SharedPreferences
    val numberFormat = NumberFormat()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_results_x, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val data = arrayUpdateW[position]
        with(holder) {
            binding.apply {
                var index = 0
                data.forEach {
                    tvNumberX.text = "W$position"
                    tvValueX.text = numberFormat.configureDecimals(typeDecimal, it)
                    index++
                }
            }
        }
    }

    override fun getItemCount(): Int = arrayUpdateW.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemResultsXBinding.bind(view)
    }

}