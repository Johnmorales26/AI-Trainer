import kotlin.math.pow
import kotlin.math.sqrt

class OperationsWithArrays {

    fun crossProductArray(ma:Array<DoubleArray>, mb:Array<DoubleArray>):Array<DoubleArray>{
        val product = Array(ma.size) { DoubleArray(mb[0].size) }
        for (i in ma.indices) {
            for (j in 0 until mb[0].size) {
                for (k in 0 until ma[0].size) {
                    product[i][j] += ma[i][k] * mb[k][j]
                }
            }
        }
        return product
    }

    fun subtractArray(ma: Array<DoubleArray>, mb: Array<DoubleArray>): Array<DoubleArray> {
        val subtract = Array(ma.size) { DoubleArray(mb[0].size) }
        var x = 0
        while (x < ma.size) {
            val numRes = ma[x][0] - mb[x][0]
            subtract[x][0] = numRes
            x++
        }
        return subtract
    }

    fun transposeArray(ma: Array<DoubleArray>): Array<DoubleArray> {
        val rows = ma.size
        val columns = ma[0].size
        val transpose = Array(columns) { DoubleArray(rows) }
        for (i in 0 until ma[0].size) {
            for (j in ma.indices) {
                transpose[i][j] = ma[j][i]
            }
        }
        return transpose
    }

    fun multiValueArray(value: Double, ma: Array<DoubleArray>): Array<DoubleArray> {
        val result = Array(ma.size) { DoubleArray(ma[0].size) }
        for (i in 0 until ma[0].size) {
            for (j in ma.indices) {
                result[j][i] = ma[j][i] * value
            }
        }
        return result
    }

    fun substractValueArray(value: Double, ma: Array<DoubleArray>): Array<DoubleArray> {
        val result = Array(ma.size) { DoubleArray(ma[0].size) }
        for (i in 0 until ma[0].size) {
            for (j in ma.indices) {
                result[j][i] = ma[j][i] - value
            }
        }
        return result
    }

    fun printArray(printArray: Array<DoubleArray>): String{
        var array = ""
        for (element in printArray) {
            for (j:Int in 0 until printArray[0].size){
                array += "${element[j]}   "
            }
            array += "\n"
        }
        return array
    }

    fun printArray(printArray: MutableList<MutableList<Double>>): String{
        var array = ""
        for (element in printArray) {
            for (j:Int in 0 until printArray[0].size){
                array += "${element[j]}   "
            }
            array += "\n"
        }
        return array
    }

    fun getJ(arrayX: Array<DoubleArray>, arrayY: Array<DoubleArray>, arrayW: Array<DoubleArray>): Array<DoubleArray> {
        val matrizMulti = crossProductArray(arrayX, arrayW)
        val multi = subtractArray(matrizMulti, arrayY)
        val multiTrans = transposeArray(multi)
        val resArray = crossProductArray(multiTrans, multi)//
        val m = (1 / (2 * arrayX.size.toString().toDouble()))
        val result = Array(resArray.size) { DoubleArray(resArray[0].size) }
        //  Result
        for (i in 0 until resArray[0].size) {
            for (j in resArray.indices) {
                result[j][i] = resArray[j][i] * m
            }
        }
        return result
    }

    fun getStop(arrayX: Array<DoubleArray>, arrayY: Array<DoubleArray>, arrayW: Array<DoubleArray>): Double {
        var resultante = 0.0
        val firstMulti = crossProductArray(arrayX, arrayW)
        val rest = subtractArray(firstMulti, arrayY)
        val arrayXTrans = transposeArray(arrayX)
        val result = crossProductArray(arrayXTrans, rest)
        result.forEach {
            resultante += it[0]
        }
        return resultante
    }

    fun getMagnitude(arrayW: Array<DoubleArray>): Double {
        var sum = 0.0
        arrayW.forEach {
            sum += (it[0] + 1).pow(2)
        }
        return sqrt(sum)
    }

    fun getRVectorial(arrayYOriginal: Array<DoubleArray>, arrayY2: Array<DoubleArray>, valueY3: Double): Double {
        val a = subtractArray(arrayYOriginal, arrayY2)
        val b = substractValueArray(valueY3, arrayYOriginal)
        val numeratorMatrix = crossProductArray(transposeArray(a), a)
        val denominatorMatrix = crossProductArray(transposeArray(b), b)
        var numerator = 0.0
        var denominator = 0.0
        numeratorMatrix.forEach {
            it.forEach { value ->
                numerator = value
            }
        }
        denominatorMatrix.forEach {
            it.forEach { value ->
                denominator = value
            }
        }
        return 1 - (numerator / denominator)
    }

}