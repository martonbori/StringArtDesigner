package hu.bme.aut.matheval

class ConstantNode (val value: Double) : Node {
    override val children: MutableList<Node> = mutableListOf()
    override fun eval(vararg variables: Variable): Double {
        return value
    }
}

