package hu.bme.aut.stringartdesigner.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import hu.bme.aut.stringartdesigner.model.geometry.Line
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.model.geometry.Point

class DrawView(ctx: Context?, attrs: AttributeSet?) : View(ctx,attrs) {

    private var paint: Paint = Paint()
    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2F
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (line in Pattern.polygon.getEdges()) {
            drawLine(canvas,line)
        }
        for (point in Pattern.points.values) {
            drawPoint(canvas, point)
        }
        for (line in Pattern.lines) {
            drawLine(canvas, line)
        }
    }

    private fun drawPoint(canvas: Canvas, point: Point) {
//        paint.textSize = 30F
//        canvas.drawText(point.n.toString(),point.pos.x, point.pos.y, paint)
        canvas.drawCircle(point.pos.x, point.pos.y,5F, paint)
    }

    private fun drawLine(canvas: Canvas, line: Line) {
        canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, paint)
    }
}