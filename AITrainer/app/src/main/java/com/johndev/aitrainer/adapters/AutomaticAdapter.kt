package com.johndev.aitriner.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.Interfaces.OnChargeData
import com.johndev.aitrainer.Models.Automatic
import com.johndev.aitrainer.Models.ChargeData
import com.johndev.aitrainer.Models.ResultsPerceptron
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemAutomaticResultsBinding
import com.johndev.aitrainer.databinding.ItemProgressDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.stream.Collectors

class AutomaticAdapter(private val dataList: MutableList<Automatic>):
    RecyclerView.Adapter<AutomaticAdapter.ViewHolder>() {

    lateinit var context: Context
    var automaticOriginal: MutableList<Automatic> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutomaticAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_automatic_results, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutomaticAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        with(holder){
            binding.apply {
                tvIterations.text = "Iteration: ${data.id.toString().trim()}"
                tvJ.text = "J: ${data.J.toString().trim()}"
                tvW0.text = "W0: ${data.W0.toString().trim()}"
                tvW1.text = "W1: ${data.W1.toString().trim()}"
                tvR.text = "R: ${data.R.toString().trim()}"
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    suspend fun add(data: Automatic) = withContext(Dispatchers.Main){
        if (!dataList.contains(data)){
            dataList.add(data)
            notifyItemInserted(dataList.size - 1)
        } else{
            update(data)
        }
    }

    fun update(data: Automatic){
        val index = dataList.indexOf(data)
        if (index != -1){
            dataList[index] = data
            notifyItemChanged(index)
        }
    }

    fun filteredOut(txtSearch: String){
        if (automaticOriginal.isEmpty()){
            automaticOriginal.addAll(dataList)
        }
        if (txtSearch.isEmpty()){
            dataList.clear()
            dataList.addAll(automaticOriginal)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val collection =  dataList.stream().filter { i -> i.id.toString().contains(txtSearch) }
                    .collect(Collectors.toList())
                dataList.clear()
                dataList.addAll(collection)
            } else {
                for (perceptron in automaticOriginal){
                    if (perceptron.id.toString().contains(txtSearch)){
                        dataList.add(perceptron)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemAutomaticResultsBinding.bind(view)
    }
}