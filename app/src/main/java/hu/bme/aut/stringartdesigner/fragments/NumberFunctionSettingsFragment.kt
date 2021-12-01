package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.FragmentGeneralSettingsBinding
import hu.bme.aut.stringartdesigner.databinding.FragmentNumberFunctionSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class NumberFunctionSettingsFragment : Fragment() {
    lateinit var binding: FragmentNumberFunctionSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNumberFunctionSettingsBinding.inflate(inflater, container, false)

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
        if(pointsFunctionStr.isNotBlank()) {
            Pattern.setPointExpression(pointsFunctionStr)
            callBack.updateCanvas()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
}