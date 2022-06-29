package com.johndev.aitrainer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.Interfaces.OnGuessErrorListener
import com.johndev.aitrainer.R
import com.johndev.aitrainer.databinding.ItemErrorGuessBinding
import java.util.*

class GuessErrorAdapter(val guessErrorList: MutableList<Double>, val listener: OnGuessErrorListener):
 RecyclerView.Adapter<GuessErrorAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_error_guess, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guessError = guessErrorList[position]
        with(holder){
            binding.apply {
                tvValue.text = guessError.toString().trim()
            }
        }
    }

    override fun getItemCount(): Int = guessErrorList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemErrorGuessBinding.bind(view)
    }

}