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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeViewModel
import com.example.lordofthegames.recyclerView.MyGameAdapter
import com.example.lordofthegames.recyclerView.OnItemListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.streams.toList


class MyGameListFragment: Fragment(), OnItemListener{

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var gameList: List<Game>
    private lateinit var pageViewModel: MyGameListViewModel
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyGameAdapter
    private lateinit var adapter2: MyGameAdapter

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
        gameList = pageViewModel.getFilt()

        adapter = MyGameAdapter(
            this,
            ViewModelProvider(requireActivity())[HomeViewModel::class.java],
            gameList,
            requireActivity()
        )
        adapter2 = MyGameAdapter(
            this,
            ViewModelProvider(requireActivity())[HomeViewModel::class.java],
            gameList.stream().filter { it.game_status == "playing" }.toList(),
            requireActivity()
        )
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
                    when(i){//TODO: EH ELALE metti la lista dei giuochi
                        1 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapter
                        }
                        2 -> {
                            textView.text = "$i/$j"
                            textView.append(tab.text)
                            recyclerView.adapter = adapter2
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
        TODO("Not yet implemented")
    }

}