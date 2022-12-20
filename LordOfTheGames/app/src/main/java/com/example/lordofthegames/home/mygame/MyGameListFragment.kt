package com.example.lordofthegames.home.mygame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.lordofthegames.R
import com.google.android.material.tabs.TabLayout


class MyGameListFragment: Fragment() {

    private var viewPager: ViewPager? = null
    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionsPagerAdapter = context?.let { cntx -> activity?.supportFragmentManager?.let { actFragMng ->
                SectionsPagerAdapter(cntx, actFragMng)
            }
        }
        //val viewPager: ViewPager = view?.findViewById(R.id.view_pager) as ViewPager

        context?.let { TabLayout(it) }?.setupWithViewPager(viewPager)
        val tabs: TabLayout? = context?.let { TabLayout(it) }
        tabs?.setupWithViewPager(viewPager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_mygame, container, false)
        viewPager = view.findViewById<View>(R.id.view_pager) as ViewPager
        viewPager?.adapter = sectionsPagerAdapter
        tabLayout = view.findViewById(R.id.tabs)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): MyGameListFragment {
            return MyGameListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}