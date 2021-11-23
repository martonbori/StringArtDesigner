package hu.bme.aut.matheval

class OperatorNode(val operation: OperatorType, override val children: MutableList<Node> = mutableListOf()) : Node {

    override fun eval(vararg variables: Variable): Double {
        var values = doubleArrayOf()
        for (child in children) {
            values += child.eval(*variables)
        }
        return operation.eval(*values)
    }
}