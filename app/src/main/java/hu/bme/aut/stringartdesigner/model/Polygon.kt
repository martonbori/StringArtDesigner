package hu.bme.aut.stringartdesigner.model

data class Polygon (
    var vertices: List<Position> = listOf(),
    var edges: List<Line> = listOf()
)
