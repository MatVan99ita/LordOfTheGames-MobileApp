package com.example.lordofthegames.home.mygame


import android.annotation.SuppressLint
import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeViewModel
import com.example.lordofthegames.recyclerView.MyGameAdapter
import com.example.lordofthegames.recyclerView.MyGameListItem
import com.example.lordofthegames.recyclerView.OnItemListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.streams.toList


class MyGameListFragment: Fragment(), OnItemListener{

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var gameList: List<MyGameListItem>
    private lateinit var playingList: List<MyGameListItem>
    private lateinit var wantedList: List<MyGameListItem>
    private lateinit var playedList: List<MyGameListItem>
    private lateinit var abandonedList: List<MyGameListItem>

    private lateinit var pageViewModel: MyGameListViewModel
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapterAll: MyGameAdapter
    private lateinit var adapterWanted: MyGameAdapter
    private lateinit var adapterPlaying: MyGameAdapter
    private lateinit var adapterPlayed: MyGameAdapter
    private lateinit var adapterAbandoned: MyGameAdapter

    private val TAB_TITLES = arrayOf(
        R.string.all,
        R.string.playing,
        R.string.plan_to_play,
        R.string.completed,
        R.string.dropped,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[MyGameListViewModel::class.java]

        val banana = requireArguments().getString("email")
        if(banana != "null") {
            gameList = banana?.let { pageViewModel.getOrderedFilt(it) }!!
            playingList = gameList.stream().filter { it.game_status == "Playing" }.toList()
            wantedList = gameList.stream().filter { it.game_status == "Wanted to play" }.toList()
            playedList = gameList.stream().filter { it.game_status == "Played" }.toList()
            abandonedList = gameList.stream().filter { it.game_status == "Abandoned" }.toList()


            adapterAll = MyGameAdapter(
                this,
                ViewModelProvider(requireActivity())[HomeViewModel::class.java],
                gameList,
                requireActivity(),
                mail = banana
            )
            adapterPlaying = MyGameAdapter(
                this,
                ViewModelProvider(requireActivity())[HomeViewModel::class.java],
                playingList,
                requireActivity(),
                mail = banana
            )
            adapterPlayed = MyGameAdapter(
                this,
                ViewModelProvider(requireActivity())[HomeViewModel::class.java],
                playedList,
                requireActivity(),
                mail = banana
            )
            adapterWanted = MyGameAdapter(
                this,
                ViewModelProvider(requireActivity())[HomeViewModel::class.java],
                wantedList,
                requireActivity(),
                mail = banana
            )
            adapterAbandoned = MyGameAdapter(
                this,
                ViewModelProvider(requireActivity())[HomeViewModel::class.java],
                abandonedList,
                requireActivity(),
                mail = banana
            )
        }
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
        recyclerView = view.findViewById(R.id.mg_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
                    when(i){
                        1 -> { // ALL
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapterAll
                        }
                        2 -> { // PLAYING
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapterPlaying
                        }
                        3 -> { // WANTED TO PLAY
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapterWanted
                        }
                        4 -> { // PLAYED
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapterPlayed
                        }
                        5 -> { // ABANDONED
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapterAbandoned
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

    override fun onItemClick(view: View, position: Int) {

    }

}