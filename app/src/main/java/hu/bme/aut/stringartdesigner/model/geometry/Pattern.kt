package hu.bme.aut.stringartdesigner.model.geometry

import com.notkamui.keval.Keval
import kotlin.math.min
import java.util.*


object Pattern {
    val points: MutableMap<Pair<Int,Int>, Point> = mutableMapOf()
    val lines: MutableList<Line> = mutableListOf()
    var polygon: Polygon = Polygon()
    var pointCount: Int = 0
    private var edgeExpression: String = ""
    private var numExpression: String = ""
    private var canvasCenter: Position = Position(0F,0F)
    private var maxSize: Int = 0

    fun setPoints(pointCnt: Int) {
        points.clear()
        pointCount = pointCnt
        for((j, e) in polygon.getEdges().withIndex()) {
            val startPos = Position(e.start.x, e.start.y)
            val gap = e.getLength() / (pointCnt+1)
            for (i in 0 until pointCnt) {
                points[Pair(j,i)] =
                    Point(startPos.translateBy(e.getDirectionVector()*(gap*(i+1))),j,i)
            }
        }
        setLines(edgeExpression, numExpression)
    }

    fun setPolygon(polygonN: Int) {
        polygon = Polygon(polygonN, canvasCenter, maxSize)
        setPoints(pointCount)
    }

    fun setLines(edgeExpression: String, numExpression: String) {
        this.edgeExpression = edgeExpression
        this.numExpression = numExpression
        if (this.edgeExpression.isBlank() || this.numExpression.isBlank())
            return
        lines.clear()
        for (p in points) {
            val point = p.value
            val keval = Keval {
                includeDefault()
                constant {
                    name = "edge"
                    value = point.edge.toDouble()
                }
                constant {
                    name = "num"
                    value = point.n.toDouble()
                }
            }
            val endpointNum = keval.eval(numExpression).toInt()
            val endpointEdge = keval.eval(edgeExpression).toInt() % polygon.vertices.size
            val endPoint = points[Pair(endpointEdge, endpointNum)]
            if (endPoint != null) {
                lines.add(Line(point.pos, endPoint.pos))
            }
        }
    }

    fun getPoint(edge: Int, num: Int) : Point? {
        return points[Pair(edge,num)]
    }

    fun getPoints() : Collection<Point> {
        return Collections.unmodifiableCollection(points.values)
    }

    fun setSize(widthPixels: Int, heightPixels: Int) {
        canvasCenter = Position(widthPixels.toFloat()/2, heightPixels.toFloat()/3)
        maxSize = min(widthPixels, heightPixels) /2 -50
        setPolygon(polygon.vertices.size)
    }


}