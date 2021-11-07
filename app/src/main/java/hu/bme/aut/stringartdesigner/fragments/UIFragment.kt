package hu.bme.aut.stringartdesigner.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.R
import hu.bme.aut.stringartdesigner.databinding.ActivityMainBinding
import hu.bme.aut.stringartdesigner.databinding.FragmentUIBinding
import hu.bme.aut.stringartdesigner.model.Pattern
import hu.bme.aut.stringartdesigner.model.Polygon

class UIFragment : Fragment() {
    lateinit var binding: FragmentUIBinding
    lateinit var callBack : PatternChanged

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUIBinding.inflate(inflater, container, false)
        binding.polygonN.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                if (str != "")
                    Pattern.setPolygon(Integer.parseInt(str))
                callBack.updateCanvas()
            }
        })
        binding.points.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                if (str != "")
                    Pattern.setPoints(Integer.parseInt(str))
                callBack.updateCanvas()
            }
        })
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = host as MainActivity
    }
    interface PatternChanged {
        fun updateCanvas()
    }

}