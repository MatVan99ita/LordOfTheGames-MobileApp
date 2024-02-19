package com.example.lordofthegames.home.mygame


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class MyGameListFragment: Fragment() {

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private var gameList: List<Game?>? = null
    private lateinit var pageViewModel: MyGameListViewModel
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var textView: TextView

    private val TAB_TITLES = arrayOf(
        R.string.all,
        R.string.playing,
        R.string.plan_to_play,
        R.string.completed,
        R.string.dropped,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[MyGameListViewModel::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        gameList = pageViewModel.getFilt()

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sectionsPagerAdapter = context?.let { context ->  SectionsPagerAdapter(context as FragmentActivity) }

        val view = inflater.inflate(R.layout.fragment_mygame, container, false)
        //val viewModel = inflater.inflate(R.layout.view_mygamelist, container, false)

        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_mygame)
        viewPager2.adapter = sectionsPagerAdapter

        //viewPager2.addView(viewModel)

            //tabLayout.tabMode = TabLayout.MODE_SCROLLABLE;
        //viewPager2.offscreenPageLimit = 4
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = context?.resources?.getString(TAB_TITLES[position])
        }.attach()
        textView = view.findViewById(R.id.mygame_textview)
        //textView.text = "1/5\nTUTTE COSE"
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activity!!.runOnUiThread {
                    textView.visibility = View.VISIBLE
                    val i = (tab.position + 1)
                    val j = tabLayout.tabCount
                    // textView.text = "$i/$j"
                    // textView.append(tab.text)
                    when(i){//TODO: EH ELALE metti la lista dei giuochi
                        1 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                        }
                        2 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                        }
                        3 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                        }
                        4 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                        }
                        5 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(1)?.select()
        return view
    }

    /**
     * TODO:
     *      5 funzioni che vanno richiamate nella sezione when
     *
     *      EXTRA: fare in modo che alla creazione della view la prima tab venga selezionata automaticamente
     */
    private fun AllGame(){

    }
    private fun CompletedGame(){

    }
    private fun PlayingGame(){

    }
    private fun AbandonedGame(){

    }
    private fun PlanToPlayGame(){

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