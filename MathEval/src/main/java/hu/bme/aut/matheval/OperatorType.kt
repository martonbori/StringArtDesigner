package hu.bme.aut.matheval

import kotlin.reflect.KFunction2

class OperatorType(val name:String, val parity: Int, val func: (DoubleArray) -> Double, val priority: Int) {

    fun eval(vararg operands: Double) : Double {
        return func(operands)
    }
}