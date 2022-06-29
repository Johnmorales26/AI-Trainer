package com.johndev.aitrainer.regresion_automatic

import NeuronTraining.NeuronTraining
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Constants.DIFFERENCES_METHOD
import com.johndev.aitrainer.Constants.NOT_VISIBLE_VIEW
import com.johndev.aitrainer.Constants.VISIBLE_VIEW
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.MainActivity.Companion.printAutomatic
import com.johndev.aitrainer.Models.Automatic
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.ChargeDatasetsFragment.Companion.valuesX
import com.johndev.aitrainer.common.ChargeDatasetsFragment.Companion.valuesY
import com.johndev.aitrainer.databinding.FragmentAutomaticCalculoBinding
import com.johndev.aitriner.Adapters.AutomaticAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.properties.Delegates

class AutomaticCalculoFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentAutomaticCalculoBinding? = null
    private val binding get() = _binding!!
    private var neuronTraining = NeuronTraining()
    private var finalW0 by Delegates.notNull<Double>()
    private var finalW1 by Delegates.notNull<Double>()
    private lateinit var adapter: AutomaticAdapter
    private var myVisibility = VISIBLE_VIEW
    private var myVisibilityTest = VISIBLE_VIEW
    private var myVisibilityResults = VISIBLE_VIEW
    private var valueR by Delegates.notNull<Double>()
    private var umbral: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticCalculoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUmbral()
        configureButtons()
        configureAnswers()
    }

    private fun configureUmbral() {
        binding.etUmbral.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                umbral = if (binding.etUmbral.text.toString().trim().isBlank()) {
                    binding.etUmbral.text = "1.0".trim().editable()
                    1.0
                } else {
                    binding.etUmbral.text.toString().trim().toDouble()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                binding.btnCalcular.isEnabled = binding.etUmbral.text.toString().trim().isNotBlank()
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun configureAnswers() {
        binding.etValueTransform.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val text = binding.etValueTransform.text.toString().trim()
                val valueTransform: Double
                if (text.isNotEmpty()){
                    valueTransform = binding.etValueTransform.text.toString().trim().toDouble()
                    val result = (valueTransform * finalW1) + finalW0
                    binding.etValueResult.text = result.toString().trim().editable()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun configureButtons() {
        binding.btnCalcular.setOnClickListener {
            binding.btnCalcular.isEnabled = false
            with(binding) {
                etValueW0.text = getString(R.string.label_load).trim().editable()
                etValueW1.text = getString(R.string.label_load).trim().editable()
                etValueIterations.text = getString(R.string.label_load).trim().editable()
                etValueJ.text = getString(R.string.label_load).trim().editable()
                etValueR.text = getString(R.string.label_load).trim().editable()
            }
            lifecycleScope.launch {
                automaticMethodForDiferences(DIFFERENCES_METHOD)
                paintResults()
            }
        }
        binding.btnMore.setOnClickListener {
            binding.recyclerView.visibility = VISIBLE
            lifecycleScope.launch(Dispatchers.Default) {
                val printList = printData()
                setupRecyclerView(printList)
            }
        }

        binding.btnDividerOp.setOnClickListener {
            if (myVisibility == VISIBLE_VIEW){
                with(binding) {
                    cvInputData.visibility = GONE
                    btnDividerOp.setImageResource(R.drawable.ic_arrow_drop_down)
                }
                myVisibility = NOT_VISIBLE_VIEW
            } else {
                with(binding) {
                    cvInputData.visibility = VISIBLE
                    btnDividerOp.setImageResource(R.drawable.ic_arrow_drop_up)
                }
                myVisibility = VISIBLE_VIEW
            }
        }

        binding.btnDividerTest.setOnClickListener {
            if (myVisibilityTest == VISIBLE_VIEW) {
                with(binding) {
                    cardViewTest.visibility = GONE
                    btnDividerTest.setImageResource(R.drawable.ic_arrow_drop_down)
                }
                myVisibilityTest = NOT_VISIBLE_VIEW
            } else {
                with(binding) {
                    cardViewTest.visibility = VISIBLE
                    btnDividerTest.setImageResource(R.drawable.ic_arrow_drop_up)
                }
                myVisibilityTest = VISIBLE_VIEW
            }
        }

        binding.btnDividerRes.setOnClickListener {
            if (myVisibilityResults == VISIBLE_VIEW) {
                with(binding) {
                    recyclerView.visibility = GONE
                    btnDividerRes.setImageResource(R.drawable.ic_arrow_drop_down)
                }
                myVisibilityResults = NOT_VISIBLE_VIEW
            } else {
                with(binding) {
                    recyclerView.visibility = VISIBLE
                    btnDividerRes.setImageResource(R.drawable.ic_arrow_drop_up)
                }
                myVisibilityResults = VISIBLE_VIEW
            }
        }
    }

    private suspend fun printData(): MutableList<Automatic> = withContext(Dispatchers.Default) {
        var x = 0
        val latestValues = resultsPerceptron.size - 10
        val returnList: MutableList<Automatic> = mutableListOf()
        lifecycleScope.launch {
            while (x < resultsPerceptron.size) {
                val adapterData = Automatic(resultsPerceptron[x].id, resultsPerceptron[x].W0,
                    resultsPerceptron[x].W1,resultsPerceptron[x].J, resultsPerceptron[x].R)
                if (x in 9999..10001) {
                    returnList.add(adapterData)
                }
                if (x in latestValues..resultsPerceptron.size){
                    returnList.add(adapterData)
                }
                x++
            }
        }
        return@withContext returnList
    }

    private suspend fun automaticMethodForDiferences(Method: String) = withContext(Dispatchers.IO) {
        resultsPerceptron = mutableListOf()
        val random = Random()
        var w0 = 0.0//random.nextInt(1..2147483647).toFloat()
        var w1 = 0.0//random.nextInt(1..2147483647).toFloat()
        var j = 0.0
        var x = 0
        var rest: Double
        var magnitude: Double
        var stop: Boolean
        do {
            //  Variables For Cycle Calculation
            val newJ = neuronTraining.getJ(w1, w0, valuesX, valuesY)
            val newW0 = neuronTraining.getAproximateW0(w0, w1, valuesX, valuesY)
            val newW1 = neuronTraining.getAproximateW1(w0, w1, valuesX, valuesY)
            val guess = neuronTraining.resolveGuess(valuesX, w1, w0)
            //  Calculate Regression Line
            val ssRegresion = neuronTraining.getSSRegresion(valuesY, guess)
            val ssTotal = neuronTraining.getSSTotal(valuesY)
            //  Variables To Stop The Loop
            val newMagnitude = neuronTraining.getResultingMagnitude(j, newW1)
            val newRest = neuronTraining.getDifferenceMagnitude(newW0, newW1)
            valueR = neuronTraining.getRSquared(ssRegresion, ssTotal)
            println("Iteracion: $x")
            println("W0 = $newW0")
            println("W1 = $newW1")
            println("J = $j")
            stop = if (Method == DIFFERENCES_METHOD) {
                //stopCycle(rest, newRest)
                magnitude = neuronTraining.getMagnitude(neuronTraining.getAproximateW0Average(w0, w1, valuesX, valuesY),
                    neuronTraining.getAproximateW1Average(w0, w1, valuesX, valuesY))
                println("$umbral > $magnitude")
                umbral!! < magnitude
            } else {
                //stopCycle(magnitude, newMagnitude)
                magnitude = neuronTraining.getMagnitude(neuronTraining.getAproximateW0Average(w0, w1, valuesX, valuesY),
                    neuronTraining.getAproximateW1Average(w0, w1, valuesX, valuesY))
                println("$umbral > $magnitude")
                umbral!! < magnitude
            }
            w0 = newW0
            w1 = newW1
            j = newJ
            resultsPerceptron.add(Automatic(x, w0, w1, j, valueR))
            printAutomatic.add(Automatic(x, w0, w1, j, valueR))
            rest = newRest
            println("Rest = $rest")
            x++
        } while (stop)
        val sound = MainActivity.sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            MediaPlayer.create(context, MainActivity.directionSound).start()
        }
        finalW0 = w0
        finalW1 = w1
    }

    private fun paintResults() {
        val reversed = resultsPerceptron.asReversed()
        binding.apply {
            binding.btnCalcular.isEnabled = true
            etValueW0.isEnabled = true
            etValueJ.isEnabled = true
            etValueIterations.isEnabled = true
            etValueW1.isEnabled = true
            etValueR.isEnabled = true
            etValueW0.text = reversed[0].W0.toString().trim().editable()
            etValueW1.text = reversed[0].W1.toString().trim().editable()
            etValueIterations.text = reversed[0].id.toString().trim().editable()
            etValueJ.text = reversed[0].J.toString().trim().editable()
            etValueR.text = valueR.toString().trim().editable()
            btnMore.isEnabled = true
            tilValueTransform.isEnabled = true
        }
        Toast.makeText(context, "Calculos Realizados...", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(printList: MutableList<Automatic>) {
        val data: MutableList<Automatic> = printList
        binding.let {
            adapter = AutomaticAdapter(data)
            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@AutomaticCalculoFragment.adapter
            }
        }
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        adapter.filteredOut(text!!)
        return false
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun Random.nextInt(range: IntRange): Int {
        return range.first + nextInt(range.last - range.first)
    }

    companion object {
        lateinit var resultsPerceptron: MutableList<Automatic>
    }

}