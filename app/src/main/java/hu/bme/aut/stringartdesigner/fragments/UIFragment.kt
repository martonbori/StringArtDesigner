package hu.bme.aut.stringartdesigner.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.stringartdesigner.MainActivity
import hu.bme.aut.stringartdesigner.PageAdapter
import hu.bme.aut.stringartdesigner.databinding.FragmentUIBinding

class UIFragment : Fragment() {
    lateinit var binding: FragmentUIBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUIBinding.inflate(inflater, container, false)
        val viewPager = binding.viewPager
        viewPager.adapter =PageAdapter(childFragmentManager)

        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        return binding.root
    }

    interface IPatternChanged {
        fun updateCanvas()
        fun animatePattern()
    }

}