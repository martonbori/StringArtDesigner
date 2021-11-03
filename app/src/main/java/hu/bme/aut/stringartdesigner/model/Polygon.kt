package hu.bme.aut.stringartdesigner.model

data class Polygon (
    var vertices: List<Position>,
    var edges: List<Line>
)
