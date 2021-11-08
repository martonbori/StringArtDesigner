package hu.bme.aut.stringartdesigner.model.geometry

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector (
    var x: Float = 0F,
    var y: Float = 0F
) {
    constructor(startPos: Position, endPos: Position) : this() {
        x = endPos.x - startPos.x
        y = endPos.y - startPos.y
    }

    fun length() : Float {
        return sqrt(x.pow(2) + y.pow(2))
    }

    fun normalize() : Vector {
        val l = length()
        return Vector(x/l, y/l)
    }

    operator fun times(a: Float) : Vector {
        return Vector(x*a, y*a)
    }
}
