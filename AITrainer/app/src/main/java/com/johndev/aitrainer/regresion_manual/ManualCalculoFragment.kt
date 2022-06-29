package com.johndev.aitrainer.regresion_manual

import NeuronTraining.NeuronTraining
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Adapters.ChargeDataAdapter
import com.johndev.aitrainer.Adapters.ResultsPerceptronAdapter
import com.johndev.aitrainer.Constants.NOT_VISIBLE_VIEW
import com.johndev.aitrainer.Constants.VISIBLE_VIEW
import com.johndev.aitrainer.DialogFragments.DialogDetailsDataFragment
import com.johndev.aitrainer.DialogFragments.DialogLoadOperationsFragment
import com.johndev.aitrainer.DialogFragments.PassData.PASS_COSTO
import com.johndev.aitrainer.DialogFragments.PassData.PASS_ID
import com.johndev.aitrainer.DialogFragments.PassData.PASS_JW
import com.johndev.aitrainer.DialogFragments.PassData.PASS_W
import com.johndev.aitrainer.Interfaces.OnChargeData
import com.johndev.aitrainer.Interfaces.OnResultsPerceptronListener
import com.johndev.aitrainer.MainActivity
import com.johndev.aitrainer.Models.ChargeData
import com.johndev.aitrainer.Models.ResultsPerceptron
import com.johndev.aitrainer.R
import com.johndev.aitrainer.common.ChargeDatasetsFragment
import com.johndev.aitrainer.common.ChargeDatasetsFragment.Companion.valuesX
import com.johndev.aitrainer.common.ChargeDatasetsFragment.Companion.valuesY
import com.johndev.aitrainer.databinding.FragmentManualCalculoBinding
import com.johndev.aitrainer.format.NumberFormat

class ManualCalculoFragment : Fragment(), OnChargeData, OnResultsPerceptronListener, SearchView.OnQueryTextListener {

    private var _binding: FragmentManualCalculoBinding? = null
    private val binding get() = _binding!!
    private var formatNum = NumberFormat()
    private var neuronTraining = NeuronTraining()
    private lateinit var adapter: ChargeDataAdapter
    private lateinit var adapterResults: ResultsPerceptronAdapter
    private var myVisibility = VISIBLE_VIEW
    private var myVisibilityResults = VISIBLE_VIEW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManualCalculoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultsPerceptron = mutableListOf()
        configureButtons()
        binding.chipIterations.setOnCheckedChangeListener { compoundButton, b -> validForms() }
        binding.chipMetaCost.setOnCheckedChangeListener { compoundButton, b -> validForms() }
    }

    private fun validForms() {
        if (binding.chipIterations.isChecked){
            binding.cvRanges.visibility = GONE
            binding.cvIterations.visibility = VISIBLE
            binding.chipMetaCost.isChecked = false
            binding.chipIterations.isChecked = true
        }
        if (binding.chipMetaCost.isChecked){
            binding.cvIterations.visibility = GONE
            binding.cvRanges.visibility = VISIBLE
            binding.chipIterations.isChecked = false
            binding.chipMetaCost.isChecked = true
        }
        if (binding.chipIterations.isChecked || binding.chipMetaCost.isChecked) binding.btnCalcular.isEnabled = true
    }

    private fun configureButtons() {
        if (!binding.chipIterations.isChecked && !binding.chipMetaCost.isChecked) binding.btnCalcular.isEnabled = false
        if (binding.chipIterations.isChecked || binding.chipMetaCost.isChecked) binding.btnCalcular.isEnabled = true
        binding.btnCalcular.setOnClickListener {
            //  Load Screen
            val dialogDetailsData = DialogLoadOperationsFragment(
                onSubmitClickListener = { quantity ->
                    Toast.makeText(context, quantity.toString(), Toast.LENGTH_SHORT).show()
                }
            )
            dialogDetailsData.show(parentFragmentManager, "dialog")
            //  Resolve Operations
            if (binding.chipIterations.isChecked){
                if (validFieldsIterator()){
                    val valueB = binding.etValueB.text.toString().trim().toDouble()
                    val valueW = binding.etValueW.text.toString().trim().toDouble()
                    val iterations = binding.etIterations.text.toString().trim().toDouble()
                    resultsPerceptron = methodIterator(valueB, valueW, iterations)
                }
            } else if (binding.chipMetaCost.isChecked){
                if (validFieldsRanges()){
                    val valueB = binding.etValueB.text.toString().trim().toDouble()
                    val valueW = binding.etValueW.text.toString().trim().toDouble()
                    val rangeA = binding.etMaxRange.text.toString().trim().toDouble()
                    val rangeB = binding.etMinRange.text.toString().trim().toDouble()
                    resultsPerceptron = methodRanges(valueW, valueB, rangeA, rangeB, valuesX, valuesY)
                }
            }
        }
        binding.btnMore.setOnClickListener {
            binding.searchView.visibility = VISIBLE
            binding.recyclerView.visibility = VISIBLE
            setupRecyclerView()
        }

        binding.btnDividerList.setOnClickListener {

        }

        binding.btnDividerOp.setOnClickListener {
            if (myVisibility == VISIBLE_VIEW) {
                binding.cvInputData.visibility = GONE
                binding.cvOptions.visibility = GONE
                binding.cvIterations.visibility = GONE
                binding.cvRanges.visibility = GONE
                binding.btnDividerOp.setImageResource(R.drawable.ic_arrow_drop_down)
                myVisibility = NOT_VISIBLE_VIEW
            } else {
                binding.cvInputData.visibility = VISIBLE
                binding.cvOptions.visibility = VISIBLE
                binding.llButtons.visibility = VISIBLE
                binding.btnDividerOp.setImageResource(R.drawable.ic_arrow_drop_up)
                myVisibility = VISIBLE_VIEW
            }
        }

        binding.btnDividerList.setOnClickListener {
            if (myVisibilityResults == VISIBLE_VIEW) {
                binding.searchView.visibility = GONE
                binding.recyclerView.visibility = GONE
                binding.btnDividerList.setImageResource(R.drawable.ic_arrow_drop_down)
                myVisibilityResults = NOT_VISIBLE_VIEW
            } else {
                binding.searchView.visibility = VISIBLE
                binding.recyclerView.visibility = VISIBLE
                binding.btnDividerList.setImageResource(R.drawable.ic_arrow_drop_up)
                myVisibilityResults = VISIBLE_VIEW
            }
        }
    }

    private fun methodIterator(valueB: Double, valueW: Double, iterations: Double): MutableList<ResultsPerceptron> {
        var counter = 1
        var w = valueW
        var derivadaRes: Double
        var costo: Double
        var valueJW: String
        var error: MutableList<Double>
        var guess: MutableList<Double>
        val results = mutableListOf<ResultsPerceptron>()
        val resume = mutableListOf<ChargeData>()
        while (counter <= iterations){
            derivadaRes = neuronTraining.resolveDerivative(w, valueB, valuesX, valuesY)
            guess = neuronTraining.resolveGuess(valuesX, w, valueB)
            error = neuronTraining.resolveError(guess, valuesY)
            valueJW = formatNum.getTwoDecimals(neuronTraining.resolveCost(w, valueB, valuesX, valuesY)).toString()
            costo = neuronTraining.resolveCost(w, valueB, valuesX, valuesY)
            println("---------- Iteracion #$counter ----------")
            println("El valor de w: ${formatNum.getTwoDecimals(w)}")
            println("El costo es de: $costo")
            println("El valor de J(w) es igual a: $valueJW")
            println("El valor de la derivada es igual a ${formatNum.getTwoDecimals(neuronTraining.resolveDerivative(w, valueB, valuesX, valuesY))}")
            println("El valor de Guess es igual a: $error")
            println("El valor de Error es igual a: $guess")
            val charge = ChargeData(counter, w, valueJW.toDouble(), costo)
            results.add(ResultsPerceptron(counter, w, valueB, iterations.toInt(), valuesX = valuesX,
                valuesY = valuesY, valueJW = valueJW.toDouble(), derivada = derivadaRes, error = error,
                guess = guess, costo = costo))
            resume.add(charge)
            w = neuronTraining.resolveW(w, derivadaRes)
            counter++
        }
        playSound()
        return results
    }

    private fun playSound() {
        val sound = MainActivity.sharedPreferences.getBoolean(getString(R.string.key_preference_enable_sound_active), true)
        if (sound){
            MediaPlayer.create(context, MainActivity.directionSound).start()
        }
    }

    private fun methodRanges(w: Double, b: Double, rango1: Double, rango2: Double, valuesX: MutableList<Double>, valuesY: MutableList<Double>)
            : MutableList<ResultsPerceptron>{
        val mayorRango: Double
        val menorRango: Double
        if (rango1 > rango2){
            mayorRango = rango1
            menorRango = rango2
        } else {
            mayorRango = rango2
            menorRango = rango1
        }
        var newW = w
        var counter = 0
        var derivadaRes: Double
        var valueJW: Double
        var error: MutableList<Double>
        var guess: MutableList<Double>
        val results = mutableListOf<ResultsPerceptron>()
        val resume = mutableListOf<ChargeData>()
        do {
            derivadaRes = neuronTraining.resolveDerivative(newW, b, valuesX, valuesY)
            guess = neuronTraining.resolveGuess(ChargeDatasetsFragment.valuesX, w, b)
            error = neuronTraining.resolveError(guess, ChargeDatasetsFragment.valuesY)
            valueJW = neuronTraining.resolveCost(newW, b, valuesX, valuesY)
            println("---------- Iteracion #$counter ----------")
            println("El valor de w: ${formatNum.getTwoDecimals(newW)}")
            println("El valor de J(w) es igual a: ${formatNum.getTwoDecimals(valueJW)}")
            println("El valor de la derivada es igual a ${formatNum.getTwoDecimals(derivadaRes)}")
            println("El valor de Error es igual a: $error")
            println("El valor de Guess es igual a: $guess")
            val charge = ChargeData(counter, newW, valueJW, valueJW)
            results.add(ResultsPerceptron(counter, newW, b, valuesX = ChargeDatasetsFragment.valuesX, valuesY = ChargeDatasetsFragment.valuesY,
                valueJW = valueJW, derivada = derivadaRes, error = error, guess = guess,
                costo = valueJW))
            resume.add(charge)
            newW = neuronTraining.resolveW(newW, derivadaRes)
            counter++
        } while (mayorRango < valueJW || menorRango > valueJW)
        resume.reverse()
        resume.forEach {
            adapter.add(it)
        }
        playSound()
        binding.btnMore.isEnabled = true
        return results
    }

    override fun onClick(chargeData: ChargeData) {
        val DialogDetailsData = DialogDetailsDataFragment(
            onSubmitClickListener = { quantity ->
                Toast.makeText(context, quantity.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        val bundle = Bundle()
        bundle.putInt(PASS_ID, chargeData.id)
        bundle.putDouble(PASS_W, chargeData.w)
        bundle.putDouble(PASS_JW, chargeData.jw)
        bundle.putDouble(PASS_COSTO, chargeData.costo)
        DialogDetailsData.arguments = bundle
        DialogDetailsData.show(parentFragmentManager, "dialog")
    }

    private fun setupRecyclerView() {
        val options = resultsPerceptron
        options.reverse()
        binding.let {
            adapterResults = ResultsPerceptronAdapter(options, listener = this)
            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@ManualCalculoFragment.adapterResults
            }
        }
    }


    override fun onQueryTextSubmit(text: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        adapterResults.filteredOut(text!!)
        return false
    }

    private fun validFieldsIterator(): Boolean{
        var isValid = true
        //  Evaluando value A
        if (binding.etValueB.text.isNullOrEmpty()){
            binding.tilValueB.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilValueB.error = null
        }
        // Evaluando value B
        if (binding.etIterations.text.isNullOrEmpty()){
            binding.tilIterations.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etIterations.error = null
        }
        // Evaluando value B
        if (binding.etValueW.text.isNullOrEmpty()){
            binding.tilValueW.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etValueW.error = null
        }
        return isValid
    }

    private fun validFieldsRanges(): Boolean {
        var isValid = true
        //  Evaluando value A
        if (binding.etValueB.text.isNullOrEmpty()){
            binding.tilValueB.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilValueB.error = null
        }
        // Evaluando value B
        if (binding.etValueW.text.isNullOrEmpty()){
            binding.tilValueW.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etValueW.error = null
        }
        // Evaluando value B
        if (binding.etMaxRange.text.isNullOrEmpty()){
            binding.tilMaxRange.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etMaxRange.error = null
        }
        // Evaluando value B
        if (binding.etMinRange.text.isNullOrEmpty()){
            binding.tilMinRange.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.etMinRange.error = null
        }
        return isValid
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

    companion object {
        lateinit var resultsPerceptron: MutableList<ResultsPerceptron>
        //lateinit var chartData: MutableList<Automatic>
    }

}