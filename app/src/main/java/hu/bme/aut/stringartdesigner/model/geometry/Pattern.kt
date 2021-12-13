package hu.bme.aut.stringartdesigner.model.geometry

import com.notkamui.keval.Keval
import com.notkamui.keval.Keval.Companion.eval
import hu.bme.aut.matheval.MathEval
import hu.bme.aut.matheval.Variable
import kotlin.math.min
import java.util.*


object Pattern {
    var points: MutableMap<Pair<Int,Int>, Point> = mutableMapOf()
    var lines: MutableList<Line> = mutableListOf()
    var plusLines: MutableList<Line> = mutableListOf()
    var polygon: Polygon = Polygon()
    var pointCount: Int = 0
    private var edgeExpression: MathEval = MathEval("edge+num")
    private var numExpression: MathEval = MathEval("num")
    private var canvasCenter: Position = Position(0F,0F)
    private var maxSize: Int = 0
    private var patternCenter: Position? = null

    fun setPoints(pointCnt: Int) {
        points.clear()
        pointCount = pointCnt
        for((j, e) in polygon.getEdges().withIndex()) {
            val startPos = Position(e.start.x, e.start.y)
            val gap = e.length / (pointCnt+1)
            for (i in 0 until pointCnt) {
                points[Pair(j,i)] =
                    Point(startPos.translateBy(e.directionVector*(gap*(i+1))),j,i)
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

    fun addPlusLine(line: Line) {
        plusLines.add(line)
    }
    fun clearPlusLines() {
        plusLines.clear()
    }

    fun addPlusLine(startPos: Position, endPos: Position) {
        plusLines.add(Line(startPos, endPos))
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

    fun translateTo(x: Int, y: Int) {
        translateBy(Vector(x - canvasCenter.x, y - canvasCenter.y))
    }

    fun translateBy(vector: Vector) {
        canvasCenter = canvasCenter.translateBy(vector)
        polygon.translateBy(vector)
        for(point in points.values) {
            point.translateBy(vector)
        }
        for (line in lines) {
            line.translateBy(vector)
        }
    }

    fun scaleTo(widthPixels: Int, heightPixels: Int) {
        val prevSize = maxSize
        maxSize = min(widthPixels, heightPixels) /2 - 25
        scaleBy(maxSize.toFloat()/prevSize)
    }

    fun scaleBy(alpha:Float) {
        for (point in points.values) {
            val fromCenterToPoint = Line (canvasCenter, point.pos)
            point.pos = point.pos.translateBy(fromCenterToPoint.directionVector*fromCenterToPoint.length*(alpha-1))
        }
    }

    fun restoreObjects(polygon: Polygon, points: MutableList<Point>, lines: MutableList<Line>, plusLines: MutableList<Line>) {
        this.polygon = polygon
        this.points.clear()
        for(point in points) {
            this.points[Pair(point.edge, point.n)] = point
        }
        this.lines = lines
        this.plusLines = plusLines
    }


}