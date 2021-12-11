package hu.bme.aut.stringartdesigner.model.geometry

import com.notkamui.keval.Keval
import com.notkamui.keval.Keval.Companion.eval
import hu.bme.aut.matheval.MathEval
import hu.bme.aut.matheval.Variable
import kotlin.math.min
import java.util.*


object Pattern {
    val points: MutableMap<Pair<Int,Int>, Point> = mutableMapOf()
    val lines: MutableList<Line> = mutableListOf()
    var polygon: Polygon = Polygon()
    var pointCount: Int = 0
    private var edgeExpression: MathEval = MathEval("edge+num")
    private var numExpression: MathEval = MathEval("num")
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
        setLines()
    }

    fun setPolygon(polygonN: Int) {
        polygon = Polygon(polygonN, canvasCenter, maxSize)
        setPoints(pointCount)
    }

    private fun setLines() {
        lines.clear()
        for (p in points) {
            val point = p.value
            val edge = point.edge.toDouble()
            val num = point.n.toDouble()

            val endpointNum = this.numExpression.eval(Variable("edge",edge), Variable("num",num)).toInt()
            val endpointEdge = this.edgeExpression.eval(Variable("edge",edge), Variable("num",num)).toInt() % polygon.vertices.size
            val endPoint = points[Pair(endpointEdge, endpointNum)]
            if (endPoint != null) {
                lines.add(Line(point.pos, endPoint.pos))
            }
        }
    }

    fun addLine(line: Line) {
        lines.add(line)
    }

    fun addLine(startPos: Position, endPos: Position) {
        lines.add(Line(startPos, endPos))
    }

    fun setEdgeExpression(expr: String) {
        edgeExpression.setExpression(expr)
        setLines()
    }

    fun setPointExpression(expr: String) {
        numExpression.setExpression(expr)
        setLines()
    }


    fun getPoint(edge: Int, num: Int) : Point? {
        return points[Pair(edge,num)]
    }

    fun getPoints() : Collection<Point> {
        return Collections.unmodifiableCollection(points.values)
    }

    fun setSize(widthPixels: Int, heightPixels: Int) {
        canvasCenter = Position(widthPixels.toFloat()/2, heightPixels.toFloat()/2)
        maxSize = min(widthPixels, heightPixels) /2 - 25
        setPolygon(polygon.vertices.size)
    }


}