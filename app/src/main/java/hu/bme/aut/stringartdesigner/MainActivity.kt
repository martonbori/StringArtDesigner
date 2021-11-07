package hu.bme.aut.stringartdesigner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.ActivityMainBinding
import hu.bme.aut.stringartdesigner.fragments.CanvasFragment
import hu.bme.aut.stringartdesigner.fragments.UIFragment
import hu.bme.aut.stringartdesigner.model.*

class MainActivity : AppCompatActivity(), UIFragment.PatternChanged {

    lateinit var binding : ActivityMainBinding
    lateinit var canvasFragment: CanvasFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        canvasFragment = supportFragmentManager.findFragmentById(R.id.canvas_fragment) as CanvasFragment
        setContentView(binding.root)
    }

    override fun updateCanvas() {
        canvasFragment.update()
    }
}