package hu.bme.aut.stringartdesigner.model

object Pattern {
    val points: MutableList<Point> = mutableListOf()
    val lines: MutableList<Line> = mutableListOf()
    var polygon: Polygon = Polygon()
    var pointCount: Int = 0

    fun setPoints(pointCnt: Int) {
        points.clear()
        pointCount = pointCnt
        for((j, e) in polygon.getEdges().withIndex()) {
            val startPos = Position(e.start.x, e.start.y)
            val gap = e.getLength() / (pointCnt+1)
            for (i in 0 until pointCnt) {
                points.add(Point(startPos.translateBy(e.getDirectionVector()*(gap*(i+1))),i,j))
            }
        }
    }

    fun setPolygon(polygonN: Int) {
        polygon = Polygon(polygonN)
        setPoints(pointCount)
    }


}