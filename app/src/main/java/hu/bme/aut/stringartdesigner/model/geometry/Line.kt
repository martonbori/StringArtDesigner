package hu.bme.aut.stringartdesigner.model.geometry

import kotlin.math.pow
import kotlin.math.sqrt

data class Line(var start: Position, var end: Position) {
    val length: Float = sqrt((end.x-start.x).pow(2) + (end.y-start.y).pow(2))
    val directionVector: Vector = Vector(start, end).normalize()
    var displayLength: Float = length
        set(l)  {
            field =
                if(l<=length) l
                else length
            scaleTo(l)
        }

    fun translateBy(vector: Vector)  {
        start.translateBy(vector)
        end.translateBy(vector)
    }
    private fun scaleBy(alpha: Float)  {
        end = end.translateBy(directionVector*alpha*(-1F))
    }
    private fun scaleTo(l: Float)  {
        end = start.translateBy(directionVector*l)
    }
    fun getScaledInstance(alpha: Float) : Line  {
        return Line(start,end.translateBy(directionVector*alpha*(-1F)))
    }
}
