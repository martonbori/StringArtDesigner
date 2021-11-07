package hu.bme.aut.stringartdesigner.model

data class Position (
    var x: Float,
    var y: Float
) {
    fun translateBy(deltaX: Int, deltaY: Int) : Position {
        return Position(x + deltaX, y + deltaY)
    }
    fun translateBy(vector: Vector) : Position{
        return Position(x + vector.x, y + vector.y)
    }
}