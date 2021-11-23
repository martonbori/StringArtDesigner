package hu.bme.aut.matheval

class VariableNode (var name: String) : Node {
    override val children: MutableList<Node> = mutableListOf()

    override fun eval(vararg variables: Variable): Double {
        for (v in variables) {
            if(v.name == name) {
                return v.value
            }
        }
        return 0.0
    }

}