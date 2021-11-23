package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.databinding.FragmentUIBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class UIFragment : Fragment() {
    lateinit var binding: FragmentUIBinding
    lateinit var callBack : PatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUIBinding.inflate(inflater, container, false)

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

        val edgeConstantA = binding.edgeConstantA
        edgeConstantA.minValue = 0
        edgeConstantA.maxValue = 100
        edgeConstantA.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val edgeConstantB = binding.edgeConstantB
        edgeConstantB.minValue = 0
        edgeConstantB.maxValue = 100
        edgeConstantB.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val edgeConstantC = binding.edgeConstantC
        edgeConstantC.minValue = 0
        edgeConstantC.maxValue = 100
        edgeConstantC.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val pointConstantA = binding.pointConstantA
        pointConstantA.minValue = 0
        pointConstantA.maxValue = 100
        pointConstantA.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }

        val pointConstantB = binding.pointConstantB
        pointConstantB.minValue = 0
        pointConstantB.maxValue = 100
        pointConstantB.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val pointConstantC = binding.pointConstantC
        pointConstantC.minValue = 0
        pointConstantC.maxValue = 100
        pointConstantC.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        return binding.root
    }

    private fun patternFunctionsChanged() {
        val pointsFunctionStr = "" + binding.pointConstantA.value + "*edge+" + binding.pointConstantB.value + "*num+" + binding.pointConstantC.value
        val edgesFunctionStr = "" + binding.edgeConstantA.value + "*edge+" + binding.edgeConstantB.value + "*num+" + binding.edgeConstantC.value
        if(pointsFunctionStr.isNotBlank() && edgesFunctionStr.isNotBlank()) {
            Pattern.setEdgeExpression(edgesFunctionStr)
            Pattern.setPointExpression(pointsFunctionStr)
            callBack.updateCanvas()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
    interface PatternChanged {
        fun updateCanvas()
    }

}