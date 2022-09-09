package com.johndev.aitrainer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.BottomOptions.BottomOptions
import com.johndev.aitrainer.Interfaces.OnBottomOptions
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemBottomOptionsBinding

class BottomOptionsAdapter(private val optionsList: MutableList<BottomOptions>, private val listener: OnBottomOptions)
    : RecyclerView.Adapter<BottomOptionsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_bottom_options, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = optionsList[position]
        holder.setListener(option)
        with(holder){
            binding.apply {
                tvName.text = option.name.trim()
            }
        }
    }

    override fun getItemCount(): Int = optionsList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemBottomOptionsBinding.bind(view)

        fun setListener(bottomOptions: BottomOptions){
            binding.root.setOnClickListener {
                listener.onClick(bottomOptions)
                true
            }
        }
    }

}