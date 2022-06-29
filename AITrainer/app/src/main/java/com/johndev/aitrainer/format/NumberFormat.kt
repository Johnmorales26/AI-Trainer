package com.johndev.aitrainer.format

import com.johndev.aitrainer.MainActivity.Companion.sharedPreferences
import com.johndev.aitrainer.R
import java.text.DecimalFormat

class NumberFormat {

    fun configureDecimals(type: String, value: Double): String {
        return when(type) {
            "pref_two_decimals" -> getTwoDecimals(value)
            "pref_four_decimals" -> getFourDecimals(value)
            "pref_six_decimals" -> getSixDecimals(value)
            "pref_eight_decimals" -> getEightDecimals(value)
            else -> getTwoDecimals(value)
        }
    }

    fun getTwoDecimals(value: Double): String = String.format("%.2f", value)
    fun getFourDecimals(value: Double): String = String.format("%.4f", value)
    fun getSixDecimals(value: Double): String = String.format("%.6f", value)
    fun getEightDecimals(value: Double): String = String.format("%.8f", value)

}