package com.johndev.aitrainer.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.R
import androidx.recyclerview.widget.RecyclerView
import com.johndev.aitrainer.databinding.ItemResultsBinding
import com.johndev.aitrainer.Interfaces.OnGuessErrorListener
import com.johndev.aitrainer.Interfaces.OnResultsPerceptronListener
import com.johndev.aitrainer.Models.ResultsPerceptron
import java.util.stream.Collectors

class ResultsPerceptronAdapter(var perceptronList: MutableList<ResultsPerceptron>, val listener: OnResultsPerceptronListener):
RecyclerView.Adapter<ResultsPerceptronAdapter.ViewHolder>(), OnGuessErrorListener{

    lateinit var context: Context
    private lateinit var adapter: GuessErrorAdapter
    var perceptronOriginal: MutableList<ResultsPerceptron> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_results, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perceptron = perceptronList[position]
        var counterError = 0
        var counterGuess = 0
        holder.apply {
            with(binding){
                tvId.text = "Operacion #${perceptron.id.toString().trim()}"
                //tvCosto.text = "El costo es de: ${perceptron.costo.toString().trim()}"
                tvW.text = "El valor de W es de: ${perceptron.valueW.toString().trim()}"
                tvJW.text = "El valor de J(w) es de: ${perceptron.valueJW.toString().trim()}"
                tvDerivada.text = "El valor de la derivada es de: ${perceptron.derivada.toString().trim()}"
                btnError.setOnClickListener {
                    if ((counterError % 2) == 0){
                        btnError.setImageResource(R.drawable.ic_arrow_drop_up)
                        binding.tlHeaderError.visibility = VISIBLE
                        binding.rvError.visibility = VISIBLE
                        counterError++
                    } else {
                        btnError.setImageResource(R.drawable.ic_arrow_drop_down)
                        binding.tlHeaderError.visibility = GONE
                        binding.rvError.visibility = GONE
                        counterError++
                    }
                }
                btnGuess.setOnClickListener {
                    if ((counterGuess % 2) == 0){
                        btnGuess.setImageResource(R.drawable.ic_arrow_drop_up)
                        binding.tlHeaderGuess.visibility = VISIBLE
                        binding.tvGuess.visibility = VISIBLE
                        counterGuess++
                    } else {
                        btnGuess.setImageResource(R.drawable.ic_arrow_drop_down)
                        binding.tlHeaderGuess.visibility = GONE
                        binding.tvGuess.visibility = GONE
                        counterGuess++
                    }
                }
                val erroOptions = perceptron.error
                binding.let {
                    adapter = GuessErrorAdapter(erroOptions, this@ResultsPerceptronAdapter)
                    rvError.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = this@ResultsPerceptronAdapter.adapter
                    }
                }
                val guessOptions = perceptron.guess
                binding.let {
                    adapter = GuessErrorAdapter(guessOptions, this@ResultsPerceptronAdapter)
                    rvGuess.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = this@ResultsPerceptronAdapter.adapter
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = perceptronList.size

    fun filteredOut(txtSearch: String){
        if (perceptronOriginal.isEmpty()){
            perceptronOriginal.addAll(perceptronList)
        }
        if (txtSearch.isEmpty()){
            perceptronList.clear()
            perceptronList.addAll(perceptronOriginal)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val collection =  perceptronList.stream().filter { i -> i.id.toString().contains(txtSearch) }
                    .collect(Collectors.toList())
                perceptronList.clear()
                perceptronList.addAll(collection)
            } else {
                for (perceptron in perceptronOriginal){
                    if (perceptron.id.toString().contains(txtSearch)){
                        perceptronList.add(perceptron)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemResultsBinding.bind(view)
    }

}