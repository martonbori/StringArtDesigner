package hu.bme.aut.stringartdesigner.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import hu.bme.aut.stringartdesigner.model.geometry.Line
import hu.bme.aut.stringartdesigner.model.geometry.Point
import hu.bme.aut.stringartdesigner.model.geometry.Polygon
import hu.bme.aut.stringartdesigner.model.geometry.Position

class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DbHelper = DbHelper(context)

    private val polygonColumns = arrayOf(
        DbConstants.Polygon.Columns.ID.name,
        DbConstants.Polygon.Columns.COORD_X.name,
        DbConstants.Polygon.Columns.COORD_Y.name
    )

    private val pointColumns = arrayOf(
        DbConstants.Points.Columns.ID.name,
        DbConstants.Points.Columns.COORD_X.name,
        DbConstants.Points.Columns.COORD_Y.name,
        DbConstants.Points.Columns.EDGE.name,
        DbConstants.Points.Columns.NUM.name
    )

    private val lineColumns = arrayOf(
        DbConstants.Lines.Columns.ID.name,
        DbConstants.Lines.Columns.START_X.name,
        DbConstants.Lines.Columns.START_Y.name,
        DbConstants.Lines.Columns.END_X.name,
        DbConstants.Lines.Columns.END_Y.name
    )

    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun persistPolygon(polygon: Polygon) {
        clearPolygon()
        for (vertex in polygon.vertices) {
            val values = ContentValues()
            values.put(DbConstants.Polygon.Columns.COORD_X.name, vertex.x)
            values.put(DbConstants.Polygon.Columns.COORD_Y.name, vertex.y)
            database!!.insert(DbConstants.Polygon.DATABASE_TABLE, null, values)
        }
    }

    fun restorePolygon(): Polygon {
        val vertices: MutableList<Position> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Polygon.DATABASE_TABLE, polygonColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val vertex: Position = cursorToPosition(cursor)
            vertices.add(vertex)
            cursor.moveToNext()
        }
        cursor.close()
        return Polygon(vertices)
    }

    fun clearPolygon() {
        database!!.delete(DbConstants.Polygon.DATABASE_TABLE, null, null)
    }

    private fun cursorToPosition(cursor: Cursor): Position {
        return Position(
            cursor.getFloat(DbConstants.Polygon.Columns.COORD_X.ordinal),
            cursor.getFloat(DbConstants.Polygon.Columns.COORD_Y.ordinal)
        )
    }

    fun persistPoints(points: List<Point>) {
        clearPoints()
        for (point in points) {
            val values = ContentValues()
            values.put(DbConstants.Points.Columns.COORD_X.name, point.pos.x)
            values.put(DbConstants.Points.Columns.COORD_Y.name, point.pos.y)
            values.put(DbConstants.Points.Columns.EDGE.name, point.edge)
            values.put(DbConstants.Points.Columns.NUM.name, point.n)
            database!!.insert(DbConstants.Points.DATABASE_TABLE, null, values)
        }
    }

    fun restorePoints(): MutableList<Point> {
        val points: MutableList<Point> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Points.DATABASE_TABLE, pointColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val point: Point = cursorToPoint(cursor)
            points.add(point)
            cursor.moveToNext()
        }
        cursor.close()
        return points
    }

    fun clearPoints() {
        database!!.delete(DbConstants.Points.DATABASE_TABLE, null, null)
    }

    private fun cursorToPoint(cursor: Cursor): Point {
        val x = cursor.getFloat(DbConstants.Points.Columns.COORD_X.ordinal)
        val y = cursor.getFloat(DbConstants.Points.Columns.COORD_Y.ordinal)
        val e = cursor.getInt(DbConstants.Points.Columns.EDGE.ordinal)
        val n = cursor.getInt(DbConstants.Points.Columns.NUM.ordinal)
        return Point(x,y,e,n)
    }

    fun persistLines(lines: List<Line>) {
        clearLines()
        for (line in lines) {
            val values = ContentValues()
            values.put(DbConstants.Lines.Columns.START_X.name, line.start.x)
            values.put(DbConstants.Lines.Columns.START_Y.name, line.start.y)
            values.put(DbConstants.Lines.Columns.END_X.name, line.end.x)
            values.put(DbConstants.Lines.Columns.END_Y.name, line.end.y)
            database!!.insert(DbConstants.Lines.DATABASE_TABLE, null, values)
        }
    }

    fun restoreLines(): MutableList<Line> {
        val lines: MutableList<Line> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Lines.DATABASE_TABLE, lineColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val line: Line = cursorToLine(cursor)
            lines.add(line)
            cursor.moveToNext()
        }
        cursor.close()
        return lines
    }

    fun clearLines() {
        database!!.delete(DbConstants.Lines.DATABASE_TABLE, null, null)
    }

    private fun cursorToLine(cursor: Cursor): Line {
        val startPos = Position(
            cursor.getFloat(DbConstants.Lines.Columns.START_X.ordinal),
            cursor.getFloat(DbConstants.Lines.Columns.START_Y.ordinal)
        )
        val endPos = Position(
            cursor.getFloat(DbConstants.Lines.Columns.END_X.ordinal),
            cursor.getFloat(DbConstants.Lines.Columns.END_Y.ordinal)
        )
        return Line(startPos, endPos)
    }
}