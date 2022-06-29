package com.johndev.aitrainer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.Models.Dataset
import com.johndev.aitrainer.Interfaces.OnDatasetListener
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemTableDataXYBinding

class DatasetAdapter(private var datasetList: MutableList<Dataset>, private val listener: OnDatasetListener)
    : RecyclerView.Adapter<DatasetAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_table_data_x_y, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataset = datasetList[position]
        holder.apply {
            with(binding){
                tvValueX.text = dataset.dataX.toString().trim()
                tvValueY.text = dataset.dataY.toString().trim()
            }
        }
    }

    override fun getItemCount(): Int = datasetList.size

    fun updateAllList(dataset: MutableList<Dataset>){
        datasetList.clear();
        var newDataset: MutableList<Dataset> = dataset
        datasetList.addAll(newDataset)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemTableDataXYBinding.bind(view)
    }

}