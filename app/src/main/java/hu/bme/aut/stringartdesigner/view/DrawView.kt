package hu.bme.aut.stringartdesigner.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.bme.aut.stringartdesigner.model.geometry.Line
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.model.geometry.Point
import hu.bme.aut.stringartdesigner.model.geometry.Position
import java.lang.Math.abs

class DrawView(ctx: Context?, attrs: AttributeSet?) : View(ctx,attrs) {

    private var paint: Paint = Paint()
    private var startPosition: Position? = null
    private var endPosition: Position? = null
    private var currentLine: Line? = null

    companion object {
        var sandboxMode : Boolean = false
    }

    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2F
        invalidate()
    }

    fun animatePattern() {
        val lines = Pattern.lines
        lines.addAll(Pattern.plusLines)
        for(line in lines) {
            ObjectAnimator.ofFloat(line,"displayLength", 0f, line.length).apply {
                duration = 2000
                start()
                addUpdateListener { updatedAnimation ->
                    invalidate()
                }
            }
        }
/*        Pattern.setPointExpression("num+0")
        ValueAnimator.ofInt(-50, 25).apply {
            duration = 5000
            start()
            addUpdateListener { updatedAnimation ->
                val value = kotlin.math.abs(updatedAnimation.animatedValue as Int)
                Pattern.setEdgeExpression("edge+$value")
                Pattern.setPointExpression("num+$value")
                invalidate()
            }
        }*/
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (line in Pattern.polygon.getEdges()) {
            drawLine(canvas, line)
        }
        for (point in Pattern.points.values) {
            drawPoint(canvas, point)
        }
        for (line in Pattern.lines) {
            drawLine(canvas, line)
        }
        for (line in Pattern.plusLines) {
          drawLine(canvas, line)
        }
        currentLine?.let { drawLine(canvas, it) }
    }

    private fun drawPoint(canvas: Canvas, point: Point) {
//        paint.textSize = 30F
//        canvas.drawText(point.n.toString(),point.pos.x, point.pos.y, paint)
        canvas.drawCircle(point.pos.x, point.pos.y,5F, paint)
    }

    private fun drawLine(canvas: Canvas, line: Line) {
        canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (sandboxMode) {
            endPosition = Position(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> startPosition = Position(event.x, event.y)
                MotionEvent.ACTION_MOVE -> {
                    currentLine = Line(startPosition!!, endPosition!!)
                }
                MotionEvent.ACTION_UP -> {
                    Pattern.addPlusLine(currentLine!!)
                    startPosition = null
                    endPosition = null
                    currentLine = null
                }
                else -> return false
            }
            invalidate()
        }
        return true
    }

}