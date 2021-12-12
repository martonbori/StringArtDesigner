package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.FragmentPointFunctionSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class PointFunctionSettingsFragment : Fragment() {
    lateinit var binding: FragmentPointFunctionSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPointFunctionSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = (host as MainActivity).getPreferences(Context.MODE_PRIVATE)

        val pointConstantA = binding.pointConstantA
        pointConstantA.minValue = 0
        pointConstantA.maxValue = 50
        pointConstantA.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }

        val pointConstantB = binding.pointConstantB
        pointConstantB.minValue = 0
        pointConstantB.maxValue = 50
        pointConstantB.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val pointConstantC = binding.pointConstantC
        pointConstantC.minValue = 0
        pointConstantC.maxValue = 50
        pointConstantC.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        return binding.root
    }

    private fun patternFunctionsChanged() {
        val pointConstantA = binding.pointConstantA.value
        val pointConstantB = binding.pointConstantB.value
        val pointConstantC = binding.pointConstantC.value
        val pointsFunctionStr = "$pointConstantA*edge+$pointConstantB*num+$pointConstantC"
        if(pointsFunctionStr.isNotBlank()) {
            Pattern.setPointExpression(pointsFunctionStr)
            callBack.updateCanvas()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
    override fun onResume() {
        getPreferences()
        super.onResume()
    }

    override fun onPause() {
        savePreferences()
        super.onPause()
    }

    private fun savePreferences() {
        val pointConstantA = binding.pointConstantA.value
        val pointConstantB = binding.pointConstantB.value
        val pointConstantC = binding.pointConstantC.value

        with(sharedPreferences.edit()) {
            putInt("point_constant_A",pointConstantA)
            putInt("point_constant_B",pointConstantB)
            putInt("point_constant_C",pointConstantC)
            apply()
        }
    }


    private fun getPreferences() {
        var default:Int = resources.getInteger(R.integer.default_value_point_constant_A)
        binding.pointConstantA.value = sharedPreferences.getInt("point_constant_A",default)
        default = resources.getInteger(R.integer.default_value_point_constant_B)
        binding.pointConstantB.value = sharedPreferences.getInt("point_constant_B",default)
        default = resources.getInteger(R.integer.default_value_point_constant_C)
        binding.pointConstantC.value = sharedPreferences.getInt("point_constant_C",default)
    }
}