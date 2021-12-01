package hu.bme.aut.stringartdesigner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hu.bme.aut.stringartdesigner.fragments.EdgeFunctionSettingsFragment
import hu.bme.aut.stringartdesigner.fragments.GeneralSettingsFragment
import hu.bme.aut.stringartdesigner.fragments.NumberFunctionSettingsFragment

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                GeneralSettingsFragment()
            }
            1 -> {
                EdgeFunctionSettingsFragment()
            }
            2 -> {
                NumberFunctionSettingsFragment()
            }
            else -> {
                GeneralSettingsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "General settings"
            }
            1 -> {
                return "Edge function"
            }
            2 -> {
                return "Number function"
            }
        }
        return super.getPageTitle(position)
    }

}
