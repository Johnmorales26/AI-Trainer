package com.johndev.aitrainer

import com.johndev.aitrainer.common.utils.checkOnlyNumbers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class AiTrainerUtilTest {

    @Test
    fun checkOnlyNumbers(){
        val isNumber = checkOnlyNumbers("12.4", "34.4", "56.4", "12.3", "12.3")
        assertThat(true, `is`(isNumber))
    }

}