package hu.bme.aut.stringartdesigner.model.geometry

import kotlin.math.cos
import kotlin.math.sin


data class Polygon(var vertices: MutableList<Position> = mutableListOf()) {
    constructor(polygonN: Int, centerPos: Position, r: Int) : this() {


        val origX = centerPos.x
        val origY = centerPos.y
        for(i in 0 until polygonN) {
            val x =
                origX + r * cos((2 * Math.PI * i / polygonN) + ((polygonN - 2) * Math.PI / (polygonN * 2))).toFloat()
            val y =
                origY + r * sin((2 * Math.PI * i / polygonN) + ((polygonN - 2) * Math.PI / (polygonN * 2))).toFloat()
            vertices.add(Position(x,y))
        }
    }
    fun getEdges() : ArrayList<Line> {
        if(vertices.isEmpty())
            return arrayListOf()
        val lines : ArrayList<Line> = arrayListOf()
        lines.add(Line(vertices.last(),vertices.first()))
        for (i in 1 until vertices.size) {
            lines.add(Line(vertices[i-1],vertices[i]))
        }
        return lines
    }
}
