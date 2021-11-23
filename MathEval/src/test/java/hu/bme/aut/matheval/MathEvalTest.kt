package hu.bme.aut.matheval

import org.junit.Assert
import org.junit.Test
import java.util.stream.IntStream

class MathEvalTest {
   @Test
    fun setExpressionTest() {
       val eval = MathEval("3+2+e")
       Assert.assertEquals(5.0, eval.eval(),0.001)
       Assert.assertEquals(6.0, eval.eval(Variable("e",1.0)),0.001)
       eval.setExpression("3*2*e")
       Assert.assertEquals(0.0, eval.eval(),0.001)
       Assert.assertEquals(6.0, eval.eval(Variable("e",1.0)),0.001)
       eval.setExpression("3*2+5")
       Assert.assertEquals(11.0, eval.eval(),0.001)
       eval.setExpression("3*2+5*4")
       Assert.assertEquals(26.0, eval.eval(),0.001)
    }
    fun plus(values: DoubleArray) :Double{
        var ret = 0.0
        for(v in values) {
            ret += v
        }
        return ret
    }
    fun times(values: DoubleArray) :Double{
        var ret:Double = values[0]
        for(i in IntStream.range(1, values.size)) {
            ret *= values[i]
        }
        return ret
    }

}