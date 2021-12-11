package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.databinding.FragmentGeneralSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.view.DrawView

class GeneralSettingsFragment : Fragment() {
    lateinit var binding: FragmentGeneralSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGeneralSettingsBinding.inflate(inflater, container, false)

        val polygonN = binding.polygonN
        polygonN.minValue = 3
        polygonN.maxValue = 100
        polygonN.setOnValueChangedListener{ _, _, newVal ->
            Pattern.setPolygon(newVal)
            callBack.updateCanvas()
        }

        val pointCount = binding.points
        pointCount.minValue = 0
        pointCount.maxValue = 100
        pointCount.setOnValueChangedListener{ _, _, newVal ->
            Pattern.setPoints(newVal)
            callBack.updateCanvas()
        }

        val sandboxMode = binding.sandbox
        sandboxMode.setOnCheckedChangeListener { btn, b ->
            DrawView.sandboxMode = b
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
}