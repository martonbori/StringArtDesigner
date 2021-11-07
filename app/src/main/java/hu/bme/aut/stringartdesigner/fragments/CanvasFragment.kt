package hu.bme.aut.stringartdesigner.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.FragmentCanvasBinding
import hu.bme.aut.stringartdesigner.model.*

class CanvasFragment : Fragment() {

    lateinit var binding : FragmentCanvasBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        super.onCreate(savedInstanceState)
        binding = FragmentCanvasBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun update() {
        binding.drawView.invalidate()
    }

}