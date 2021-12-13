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
import hu.bme.aut.stringartdesigner.databinding.FragmentEdgeFunctionSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern

class EdgeFunctionSettingsFragment : Fragment(), MainActivity.IMenuEventListener {
    lateinit var binding: FragmentEdgeFunctionSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEdgeFunctionSettingsBinding.inflate(inflater, container, false)


        val edgeConstantA = binding.edgeConstantA
        edgeConstantA.minValue = 0
        edgeConstantA.maxValue = 50
        edgeConstantA.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val edgeConstantB = binding.edgeConstantB
        edgeConstantB.minValue = 0
        edgeConstantB.maxValue = 50
        edgeConstantB.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        val edgeConstantC = binding.edgeConstantC
        edgeConstantC.minValue = 0
        edgeConstantC.maxValue = 50
        edgeConstantC.setOnValueChangedListener{ _, _, _ ->
            patternFunctionsChanged()
        }
        return binding.root
    }

    override fun onResume() {
        loadPattern("persistent")
        super.onResume()
    }

    override fun onPause() {
        savePattern("persistent")
        super.onPause()
    }

    private fun patternFunctionsChanged() {
        val edgeConstantA = binding.edgeConstantA.value
        val edgeConstantB = binding.edgeConstantB.value
        val edgeConstantC = binding.edgeConstantC.value
        val edgesFunctionStr = "$edgeConstantA*edge+$edgeConstantB*num+$edgeConstantC"
        if(edgesFunctionStr.isNotBlank()) {
            Pattern.setEdgeExpression(edgesFunctionStr)
            callBack.updateCanvas()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
        (host as MainActivity).addMenuListener(this)
    }

    override fun savePattern(name: String) {
        val edgeConstantA = binding.edgeConstantA.value
        val edgeConstantB = binding.edgeConstantB.value
        val edgeConstantC = binding.edgeConstantC.value
        val sharedPreferences = requireActivity().getSharedPreferences(name,Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("edge_constant_A",edgeConstantA)
            putInt("edge_constant_B",edgeConstantB)
            putInt("edge_constant_C",edgeConstantC)
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
        var default:Int = resources.getInteger(R.integer.default_value_edge_constant_A)
        binding.edgeConstantA.value = sharedPreferences.getInt("edge_constant_A",default)
        default = resources.getInteger(R.integer.default_value_edge_constant_B)
        binding.edgeConstantB.value = sharedPreferences.getInt("edge_constant_B",default)
        default = resources.getInteger(R.integer.default_value_edge_constant_C)
        binding.edgeConstantC.value = sharedPreferences.getInt("edge_constant_C",default)
        patternFunctionsChanged()
    }
}