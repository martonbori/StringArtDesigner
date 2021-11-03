package hu.bme.aut.stringartdesigner.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import hu.bme.aut.stringartdesigner.model.*

class DrawView(ctx: Context?, attrs: AttributeSet?) : View(ctx,attrs) {

    private var paint: Paint = Paint()
    private var pattern: Pattern
    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        pattern = Pattern()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (point in pattern.points) {
            drawPoint(canvas, point)
        }
        for (line in pattern.lines) {
            drawLine(canvas, line)
        }
    }

    private fun drawPoint(canvas: Canvas, point: Point) {
        canvas.drawPoint(point.pos.x, point.pos.y,paint)
    }

    private fun drawLine(canvas: Canvas, line: Line) {
        canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, paint)
    }

    fun loadPattern(pattern: Pattern) {
        this.pattern = pattern
        invalidate()
    }

}