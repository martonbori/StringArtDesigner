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
import hu.bme.aut.stringartdesigner.databinding.FragmentGeneralSettingsBinding
import hu.bme.aut.stringartdesigner.model.geometry.Pattern
import hu.bme.aut.stringartdesigner.view.DrawView

class GeneralSettingsFragment : Fragment(), MainActivity.IMenuEventListener {
    lateinit var binding: FragmentGeneralSettingsBinding
    lateinit var callBack : UIFragment.IPatternChanged


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGeneralSettingsBinding.inflate(inflater, container, false)

        val polygonN = binding.polygonN
        polygonN.minValue = 3
        polygonN.maxValue = 25
        polygonN.setOnValueChangedListener{ _, _, newVal ->
            Pattern.setPolygon(newVal)
            callBack.updateCanvas()
        }

        val pointCount = binding.points
        pointCount.minValue = 0
        pointCount.maxValue = 50
        pointCount.setOnValueChangedListener{ _, _, newVal ->
            Pattern.setPoints(newVal)
            callBack.updateCanvas()
        }

        val sandboxMode = binding.sandbox
        sandboxMode.setOnCheckedChangeListener { btn, b ->
            if(!b) Pattern.clearPlusLines()
            DrawView.sandboxMode = b
            callBack.updateCanvas()
        }

        val animateBtn = binding.animateBtn
        animateBtn.setOnClickListener {
            callBack.animatePattern()
        }


        return binding.root
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
        with(sharedPreferences.edit()) {
            putInt("polygon_n",binding.polygonN.value)
            putInt("point_count",binding.points.value)
            putBoolean("sandbox_mode",binding.sandbox.isChecked)
            apply()
        }
    }

    override fun loadPattern(name: String) {
        val sharedPreferences = requireActivity().getSharedPreferences(name,Context.MODE_PRIVATE)
        val defaultPN:Int = resources.getInteger(R.integer.default_value_polygonN)
        binding.polygonN.value = sharedPreferences.getInt("polygon_n",defaultPN)
        val defaultPC:Int = resources.getInteger(R.integer.default_value_number_of_points)
        binding.points.value = sharedPreferences.getInt("point_count",defaultPC)
        val defaultSM:Boolean = resources.getBoolean(R.bool.default_value_sandbox_mode)
        binding.sandbox.isChecked = sharedPreferences.getBoolean("sandbox_ode",defaultSM)
    }

}