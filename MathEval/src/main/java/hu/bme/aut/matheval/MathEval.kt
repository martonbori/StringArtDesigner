package hu.bme.aut.matheval


class MathEval(expr: String) {
    private lateinit var expression : Node
    private var operatorTypes: Array<MutableMap<String,OperatorType>> = Array(5) { mutableMapOf() }

    init {
        addOperator("+",2,{ a -> a[0]+a[1]},1)
        addOperator("*",2,{a -> a[0]*a[1]},2)
        setExpression(expr)
    }

    fun setExpression(expr: String) {
        expression = getExpressionTree(expr)
    }

    private fun getExpressionTree(expr: String) : Node {
        val operator = findNextOperator(expr)
        val returnNode : Node
        if (operator!= null) {
            returnNode = OperatorNode(operator.second)
            returnNode.children.add(getExpressionTree(expr.substring(0 until operator.first)))
            returnNode.children.add(getExpressionTree(expr.substring(operator.first+1 until expr.length)))
            return returnNode
        }
        val value = expr.toDoubleOrNull()
        return if(value != null)
            ConstantNode(value)
        else VariableNode(expr)
    }

    private fun findNextOperator(expr: String) : Pair<Int, OperatorType>? {
        for (i in 1..4) {
            val op = expr.findAnyOf(operatorTypes[i].keys)
            if (op != null) {
                return Pair(op.first, operatorTypes[i][op.second]!!)
            }
        }
        return null
    }

    fun addOperator(name:String, parity: Int, func: (DoubleArray) -> Double, priority: Int) {
        operatorTypes[priority][name] = OperatorType(name, parity, func, priority)
    }

    fun eval(vararg variables: Variable) : Double {
        return expression.eval(*variables)
    }
}