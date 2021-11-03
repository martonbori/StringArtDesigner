package hu.bme.aut.stringartdesigner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.ActivityMainBinding
import hu.bme.aut.stringartdesigner.model.Line
import hu.bme.aut.stringartdesigner.model.Pattern
import hu.bme.aut.stringartdesigner.model.Point
import hu.bme.aut.stringartdesigner.model.Position

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val pattern = Pattern()
        pattern.points.add(Point(Position(250F,250F),0,0))
        pattern.points.add(Point(Position(750F,250F),1,0))
        pattern.points.add(Point(Position(750F,750F),2,0))
        pattern.points.add(Point(Position(250F,750F),3,0))
        pattern.lines.add(Line(pattern.points[0].pos, pattern.points[1].pos))
        pattern.lines.add(Line(pattern.points[1].pos, pattern.points[2].pos))
        pattern.lines.add(Line(pattern.points[2].pos, pattern.points[3].pos))
        pattern.lines.add(Line(pattern.points[3].pos, pattern.points[0].pos))
        pattern.lines.add(Line(pattern.points[0].pos, pattern.points[2].pos))

        binding.drawView.loadPattern(pattern)
        setContentView(binding.root)
    }
}