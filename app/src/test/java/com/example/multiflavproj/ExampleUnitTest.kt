package com.example.multiflavproj

import android.text.TextUtils
import androidx.core.text.isDigitsOnly
import com.example.multiflavproj.Util.computeEquation
import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern
import com.example.multiflavproj.Util.getEquation
import com.example.multiflavproj.Util.isBasicOperation


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testGetEquation() {
        var string = "askdja1000asdasd+asdasd22 ++ 333+44asdasdas1+1"

        assertEquals(
            "333+44",
            Util.getEquation(string)
        )
    }

    @Test
    fun testComputeEquation() {
        assertEquals(
            5,
            "2+3".computeEquation()
        )
    }

    @Test
    fun test2() {
        var string = "10"

        println(string.all {
            it.isDigit()
        })
    }
}