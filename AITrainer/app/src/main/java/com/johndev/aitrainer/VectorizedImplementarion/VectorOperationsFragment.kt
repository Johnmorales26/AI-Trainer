package com.johndev.aitrainer.VectorizedImplementarion

import OperationsWithArrays
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.aitrainer.Models.ChartVectorData
import com.johndev.aitrainer.R
import com.johndev.aitrainer.VectorizedImplementarion.VectorDatasetsFragment.Companion.sendArrayX
import com.johndev.aitrainer.VectorizedImplementarion.VectorDatasetsFragment.Companion.sendArrayY
import com.johndev.aitrainer.VectorizedImplementarion.VectorDatasetsFragment.Companion.sendArrayW
import com.johndev.aitrainer.adapters.PrintDataVectoresAdapter
import com.johndev.aitrainer.databinding.FragmentVectorOperationsBinding
import com.johndev.aitrainer.format.NumberFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VectorOperationsFragment : Fragment() {

    private var _binding: FragmentVectorOperationsBinding? = null
    private val binding get() = _binding!!
    private var umbral: Double? = null
    private var printData: MutableList<PrintDataVectores> = mutableListOf()
    private lateinit var adapter: PrintDataVectoresAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVectorOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        configureUmbral()
        configureButtons()
    }

    private fun configureUmbral() {
        binding.etUmbral.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                umbral = if (binding.etUmbral.text.toString().trim().isNullOrBlank()) {
                    binding.etUmbral.text = "1.0".trim().editable()
                    1.0
                } else {
                    binding.etUmbral.text.toString().trim().toDouble()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                binding.btnCalcular.isEnabled = !binding.etUmbral.text.toString().trim().isNullOrBlank()
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun configureButtons() {
        binding.btnCalcular.setOnClickListener {
            paintLoadData()
            lifecycleScope.launch {
                val array = matrizNeuron(sendArrayX, sendArrayY, sendArrayW)
                paintResult(array)
            }
        }
    }

    private fun paintLoadData() {
        with(binding) {
            etValueJ.text = getString(R.string.label_load).trim().editable()
            etValueR.text = getString(R.string.label_load).trim().editable()
            etValueIterations.text = getString(R.string.label_load).trim().editable()
        }
    }

    private suspend fun matrizNeuron(arrayXz: Array<DoubleArray>, arrayYz: Array<DoubleArray>, arrayWz: Array<DoubleArray> ) = withContext(Dispatchers.IO) {
        val arrayX = arrayXz
        val arrayY = arrayYz
        var arrayW = arrayWz
        var arrayUpdateW = Array(arrayX[0].size) { DoubleArray(1) }
        val alpha = 0.002
        var valueJ = 0.0
        var index = 0
        var magnitude: Double
        val operationsWithArrays = OperationsWithArrays()
        println("Array X --> \n${operationsWithArrays.printArray(arrayX)}")
        println("Array Y --> \n${operationsWithArrays.printArray(arrayY)}")
        println("Array W --> \n${operationsWithArrays.printArray(arrayW)}")
        chartVectorData = mutableListOf()
        do {
            //  Paso 0 -------------------------------------------------------------------------
            titleSteps("Paso 0")
            println("Matriz A " + arrayX.size + "x"+ arrayX[0].size)
            println(operationsWithArrays.printArray(arrayX))
            println("Matriz B " + arrayW.size + "x"+ arrayW[0].size)
            println(operationsWithArrays.printArray(arrayW))
            //  Paso 1 -------------------------------------------------------------------------
            titleSteps("Paso 1")
            val arrayRes = operationsWithArrays.crossProductArray(arrayX, arrayW)
            println("Matriz A " + arrayRes.size + "x"+ arrayRes[0].size)
            println(operationsWithArrays.printArray(arrayRes))
            //  Calculate R
            var average = 0.0
            var indexR = 0
            arrayRes.forEach { internArray ->
                internArray.forEach {
                    average += it
                    indexR++
                }
            }
            average /= indexR
            val valueR = operationsWithArrays.getRVectorial(arrayY, arrayRes, average)
            //  Paso 2 -------------------------------------------------------------------------
            titleSteps("Paso 2")
            val resSubtract = operationsWithArrays.subtractArray(arrayRes, arrayY)
            println(operationsWithArrays.printArray(resSubtract))
            //  Paso 3 -------------------------------------------------------------------------
            titleSteps("Paso 3")
            val transposeArray = operationsWithArrays.transposeArray(arrayX)
            println(operationsWithArrays.printArray(transposeArray))
            //  Paso 4 -------------------------------------------------------------------------
            titleSteps("Paso 4")
            val stepFour = operationsWithArrays.crossProductArray(transposeArray, resSubtract)
            println(operationsWithArrays.printArray(stepFour))
            //  Paso 5 -------------------------------------------------------------------------
            titleSteps("Paso 5")
            val j = (1 / arrayX.size.toString().toDouble())
            val multiValue = alpha * (j)
            val stepFive = operationsWithArrays.multiValueArray(multiValue, stepFour)
            println(operationsWithArrays.printArray(stepFive))
            //  Paso 6 -------------------------------------------------------------------------
            titleSteps("Paso 6 - Resultado De Una Epoca")
            arrayUpdateW = operationsWithArrays.subtractArray(arrayW, stepFive)
            println(operationsWithArrays.printArray(arrayUpdateW))
            //  Paso 7 -------------------------------------------------------------------------
            titleSteps("Paso 7")
            val newJ = operationsWithArrays.getJ(arrayX, arrayY, arrayW)
            println(operationsWithArrays.printArray(newJ))
            newJ.forEach {
                valueJ = it[0]
            }
            arrayW = arrayUpdateW
            println("---------- Iteration $index ----------")
            val getStop = operationsWithArrays.crossProductArray(operationsWithArrays.transposeArray(arrayX), operationsWithArrays.subtractArray(operationsWithArrays.crossProductArray(arrayX, arrayW), arrayY))
            println(operationsWithArrays.printArray(getStop))
            magnitude = operationsWithArrays.getMagnitude(getStop)
            println("Magnitude --> $magnitude")
            //  Index, Ws, J, R2
            printData.add(PrintDataVectores(index, valueJ, valueR))
            chartVectorData.add(ChartVectorData(index, valueJ, arrayW))
            index++
        } while (umbral!! < magnitude)
        return@withContext arrayUpdateW
    }

    private fun paintResult(arrayUpdateW: Array<DoubleArray>) {
        val numberFormat = NumberFormat()
        val type = sharedPreferences.getString(getString(R.string.key_preference_decimal_numbers),
            getString(R.string.preferences_two_decimals))
        val index = printData.size - 1
        with(binding) {
            tvTitleWs.visibility = VISIBLE
            updateRecyclerView(type, arrayUpdateW)
            etValueJ.isEnabled = true
            etValueJ.text = numberFormat.configureDecimals(type!!, printData[index].J).editable()
            etValueIterations.isEnabled = true
            etValueIterations.text = printData[index].index.toString().trim().editable()
            etValueR.isEnabled = true
            etValueR.text = numberFormat.configureDecimals(type, printData[index].R).editable()
        }
    }

    private fun updateRecyclerView(type: String?, arrayUpdateW: Array<DoubleArray>) {
        binding.let {
            adapter = PrintDataVectoresAdapter(arrayUpdateW, type.toString())
            it.recyclerView.apply {
                //layoutManager = LinearLayoutManager(context)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@VectorOperationsFragment.adapter
            }
        }
    }

    private fun titleSteps(step: String) {
        println("-----------------------------------------------")
        println("-------------------- $step --------------------")
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    companion object {
        lateinit var chartVectorData: MutableList<ChartVectorData>
    }

}