package hu.bme.aut.stringartdesigner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.databinding.FragmentCanvasBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class CanvasFragment : Fragment(){

    lateinit var binding : FragmentCanvasBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        super.onCreate(savedInstanceState)
        binding = FragmentCanvasBinding.inflate(inflater, container, false)

        binding.root.post {
            val height: Int = binding.root.measuredHeight
            val width: Int = binding.root.measuredWidth
            Pattern.translateTo(width/2, height/2)
            Pattern.scaleTo(width, height)
            update()
        }

        return binding.root
    }

    fun update() {
        binding.drawView.invalidate()
    }

    fun animatePattern() {
        binding.drawView.animatePattern()
    }

}