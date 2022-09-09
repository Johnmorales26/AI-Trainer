package com.johndev.aitrainer.common.utils

import java.lang.NumberFormatException

fun checkOnlyNumbers(vararg number: String): Boolean {
    var isNumber = false
    number.forEach {
        if (it.matches(Regex("[+-]?\\d*(\\.\\d+)?"))) {
            println(it)
            isNumber = true
        } else {
            isNumber = false
        }
    }
    return isNumber
}