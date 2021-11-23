package hu.bme.aut.matheval

interface Node {
    val children : MutableList<Node>
    fun eval(vararg variables: Variable): Double
}