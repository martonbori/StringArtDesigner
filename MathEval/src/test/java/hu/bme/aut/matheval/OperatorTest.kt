package hu.bme.aut.matheval

import org.junit.Assert
import org.junit.Test
import java.util.stream.IntStream.range

class OperatorTest {
    @Test
    fun addition_isCorrect() {
        val opType = OperatorType("+",2, ::plus,1)
        Assert.assertEquals(7.0, opType.eval(2.0,5.0),0.001)
    }
    fun plus(values: DoubleArray) :Double{
        var ret = 0.0
        for(v in values) {
            ret += v
        }
        return ret
    }

    @Test
    fun multiplication_isCorrect() {
        val opType = OperatorType("*",2, ::times,2)
        Assert.assertEquals(10.0, opType.eval(2.0,5.0),0.001)
    }
    fun times(values: DoubleArray) :Double{
        var ret:Double = values[0]
        for(i in range(1,values.size)) {
            ret *= values[i]
        }
        return ret
    }
}

