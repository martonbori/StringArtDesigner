package hu.bme.aut.stringartdesigner.model.geometry

import kotlin.math.pow
import kotlin.math.sqrt

data class Line(var start: Position, var end: Position) {

    fun getLength() : Float {
        return sqrt((end.x-start.x).pow(2) + (end.y-start.y).pow(2))
    }
    fun getDirectionVector() : Vector {
        return Vector(start, end).normalize()
    }
    fun translateBy(vector: Vector)  {
        start.translateBy(vector)
        end.translateBy(vector)
    }
}
