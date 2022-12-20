package com.example.lordofthegames.home.mygame


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.lordofthegames.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab


class MyGameListFragment: Fragment() {

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var pageViewModel: AllGameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[AllGameView::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        sectionsPagerAdapter = context?.let { context -> activity?.supportFragmentManager?.let { actFragMng ->
                SectionsPagerAdapter(context, actFragMng)
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews(view);
        val view: View = inflater.inflate(R.layout.fragment_mygame, container, false)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        viewPager?.adapter = sectionsPagerAdapter
        viewPager?.offscreenPageLimit = 3
        val vp: ViewPager? = context?.let { ViewPager(it) }
        vp?.offscreenPageLimit = 3
        vp?.adapter = sectionsPagerAdapter
        val tablayout: TabLayout = view.findViewById(R.id.tab_mygame)
        tablayout.setupWithViewPager(viewPager)
        //TAB_TITLES.forEach { el -> tablayout.addTab(tablayout.newTab().setText(getString(el))) }
        val textView: TextView = view.findViewById(R.id.textView3)
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return view
        //super.onCreateView(inflater, container, savedInstanceState)
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