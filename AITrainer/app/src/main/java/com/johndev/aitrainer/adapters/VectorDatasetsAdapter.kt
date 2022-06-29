package com.johndev.aitrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemResultsXBinding
import com.johndev.aitrainer.databinding.ItemVectorDatasetsBinding

class VectorDatasetsAdapter(val listMultiLines: MutableList<MutableList<Double>>):
    RecyclerView.Adapter<VectorDatasetsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_vector_datasets, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listMultiLines[position]
    }

    override fun getItemCount(): Int = listMultiLines.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemVectorDatasetsBinding.bind(view)
    }

}