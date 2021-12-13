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

class PointFunctionSettingsFragment : Fragment(), MainActivity.IMenuEventListener {
    lateinit var binding: FragmentPointFunctionSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPointFunctionSettingsBinding.inflate(inflater, container, false)

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
        (host as MainActivity).addMenuListener(this)
    }
    override fun onResume() {
        loadPattern("persistent")
        super.onResume()
    }

    override fun onPause() {
        savePattern("persistent")
        super.onPause()
    }

    override fun savePattern(name: String) {
        val sharedPreferences = requireActivity().getSharedPreferences(name,Context.MODE_PRIVATE)
        val pointConstantA = binding.pointConstantA.value
        val pointConstantB = binding.pointConstantB.value
        val pointConstantC = binding.pointConstantC.value

        with(sharedPreferences.edit()) {
            putInt("point_constant_A",pointConstantA)
            putInt("point_constant_B",pointConstantB)
            putInt("point_constant_C",pointConstantC)
            apply()
        }
        val saveFiles = requireActivity().getSharedPreferences("saves",Context.MODE_PRIVATE)
        if(!saveFiles.all.containsValue(name)) {
            with(saveFiles.edit()) {
                putString(saveFiles.all.size.toString(),name)
                apply()
            }
        }
    }

    override fun loadPattern(name: String) {
        val sharedPreferences = requireActivity().getSharedPreferences(name,Context.MODE_PRIVATE)
        var default:Int = resources.getInteger(R.integer.default_value_point_constant_A)
        binding.pointConstantA.value = sharedPreferences.getInt("point_constant_A",default)
        default = resources.getInteger(R.integer.default_value_point_constant_B)
        binding.pointConstantB.value = sharedPreferences.getInt("point_constant_B",default)
        default = resources.getInteger(R.integer.default_value_point_constant_C)
        binding.pointConstantC.value = sharedPreferences.getInt("point_constant_C",default)
    }
}