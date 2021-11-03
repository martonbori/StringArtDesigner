package hu.bme.aut.stringartdesigner.model

data class Pattern (
    val points: MutableList<Point> = mutableListOf(),
    val lines: MutableList<Line> = mutableListOf(),
    val polygon: Polygon = Polygon(),
)