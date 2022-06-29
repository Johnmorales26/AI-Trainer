package com.johndev.aitrainer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.Interfaces.OnChargeData
import com.johndev.aitrainer.Models.ChargeData
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemProgressDataBinding

class ChargeDataAdapter(private val dataList: MutableList<ChargeData>, private val listener: OnChargeData):
RecyclerView.Adapter<ChargeDataAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_progress_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.setListener(data)
        with(holder){
            binding.apply {
                tvId.text = "Iteration: ${data.id.toString().trim()}"
                tvW.text = "W: ${data.w.toString().trim()}"
                tvJW.text = "J(w) ${data.jw.toString().trim()}"
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun add(chargeData: ChargeData){
        if (!dataList.contains(chargeData)){
            dataList.add(chargeData)
            notifyItemInserted(dataList.size - 1)
        } else{
            update(chargeData)
        }
    }

    fun update(chargeData: ChargeData){
        val index = dataList.indexOf(chargeData)
        if (index != -1){
            dataList[index] = chargeData
            notifyItemChanged(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemProgressDataBinding.bind(view)

        fun setListener(chargeData: ChargeData){
            binding.root.setOnClickListener {
                listener.onClick(chargeData)
                true
            }
        }
    }

}