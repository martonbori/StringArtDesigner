package hu.bme.aut.stringartdesigner.model

data class Polygon(var vertices: MutableList<Position> = mutableListOf()) {
    constructor(polygonN: Int) : this() {
        val origX = 500F
        val origY = 500F
        val r = 400
        for(i in 0 until polygonN) {
            val x = origX + r * Math.cos((2 * Math.PI * i / polygonN)+((polygonN-2) * Math.PI / (polygonN*2))).toFloat()
            val y = origY + r * Math.sin((2 * Math.PI * i / polygonN)+((polygonN-2) * Math.PI / (polygonN*2))).toFloat()
            vertices.add(Position(x,y))
        }
    }
    fun getEdges() : ArrayList<Line> {
        if(vertices.isEmpty())
            return arrayListOf()
        val lines : ArrayList<Line> = arrayListOf()
        lines.add(Line(vertices.first(),vertices.last()))
        for (i in 1 until vertices.size) {
            lines.add(Line(vertices[i-1],vertices[i]))
        }
        return lines
    }
}
