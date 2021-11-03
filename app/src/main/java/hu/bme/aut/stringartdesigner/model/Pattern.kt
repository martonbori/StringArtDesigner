package hu.bme.aut.stringartdesigner.model

data class Pattern (
    var polygon: Polygon,
    var points: List<Point>,
    var lines: List<Line>
)