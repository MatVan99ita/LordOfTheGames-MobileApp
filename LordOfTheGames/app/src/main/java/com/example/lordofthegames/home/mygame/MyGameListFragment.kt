package com.example.lordofthegames.home.mygame


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.lordofthegames.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MyGameListFragment: Fragment() {

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var pageViewModel: AllGameView
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val TAB_TITLES = arrayOf(
        R.string.all,
        R.string.playing,
        R.string.completed,
        R.string.dropped,
        R.string.plan_to_play,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*pageViewModel = ViewModelProvider(this)[AllGameView::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViews(view);
        sectionsPagerAdapter = context?.let { context ->  SectionsPagerAdapter(context as FragmentActivity) }
        val view = inflater.inflate(R.layout.fragment_mygame, container, false)

        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_mygame)
        viewPager2.adapter = sectionsPagerAdapter
        //tabLayout.tabMode = TabLayout.MODE_SCROLLABLE;
        viewPager2.offscreenPageLimit = 4
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = context?.resources?.getString(TAB_TITLES[position])
        }.attach()

        //tabLayout.setupWithViewPager(viewPager2)
        //tabLayout.tabGravity = TabLayout.GRAVITY_CENTER;
        //val vp: ViewPager? = context?.let { ViewPager(it) }
        //vp?.offscreenPageLimit = 2
        //vp?.adapter = sectionsPagerAdapter
        //
        //TAB_TITLES.forEach { el -> tablayout.addTab(tablayout.newTab().setText(getString(el))) }
        return view
    }

    private fun initViews(view: View?) {
        //val textView: TextView = requireView().findViewById(R.id.commonTextView)
        //textView.text = "Category :  " + arguments!!.getInt("position")
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