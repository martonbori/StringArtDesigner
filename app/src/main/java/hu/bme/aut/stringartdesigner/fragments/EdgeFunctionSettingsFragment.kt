package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.FragmentEdgeFunctionSettingsBinding
import hu.bme.aut.stringartdesigner.databinding.FragmentNumberFunctionSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class EdgeFunctionSettingsFragment : Fragment() {
    lateinit var binding: FragmentEdgeFunctionSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEdgeFunctionSettingsBinding.inflate(inflater, container, false)

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
        return binding.root
    }

    private fun patternFunctionsChanged() {
        val edgesFunctionStr = "" + binding.edgeConstantA.value + "*edge+" + binding.edgeConstantB.value + "*num+" + binding.edgeConstantC.value
        if(edgesFunctionStr.isNotBlank()) {
            Pattern.setEdgeExpression(edgesFunctionStr)
            callBack.updateCanvas()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
}