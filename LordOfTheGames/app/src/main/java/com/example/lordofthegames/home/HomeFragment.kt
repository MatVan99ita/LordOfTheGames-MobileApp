package com.example.lordofthegames.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.GameDeatils.GameDetActivity
import com.example.lordofthegames.GameDeatils.GameDetFragment
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentHomeBinding
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CardItem
import com.example.lordofthegames.recyclerView.OnItemListener


class HomeFragment: Fragment(), OnItemListener {

    private var gameItems: MutableList<CardItem> = listOf(
        CardItem("ic__search_white_24", "Spado spado uccidi uccidi"),
        CardItem("ic_menu_24dp",        "Sparo sparo uccidi uccidi"),
        CardItem("ic_t_pose",           "Matel Gear Rising: Revengence"),
        CardItem("ic_t_pose",           "Dark Souls 3"),
        CardItem("ic_t_pose",           "MARVEL Spider-Man"),
        CardItem("ic_t_pose",           "Bloodborne"),
        CardItem("ic_t_pose",           "God of War: Ragnarok"),
        CardItem("ic_t_pose",           "Gabibbo"), // */
        CardItem("yee",                 "Dark Souls 3"),
        CardItem("yee",                 "MARVEL Spider-Man"),
        CardItem("gabibbo",             "Dark Souls 3"),
        CardItem("gabibbo",             "MARVEL Spider-Man"),
        CardItem("gabibbo",             "Horizon Zero Dawn: Forbidden West"),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo",             "Dark Souls 3"),
        CardItem("yee",                 "MARVEL Spider-Man"),
        CardItem("gabibbo",             "Bloodborne"),
        CardItem("gabibbo",             "Dark Souls 3"),
        CardItem("yee",                 "MARVEL Spider-Man"),
        CardItem("gabibbo",             "Bloodborne"),
    ) as MutableList<CardItem>

    private var adapter: CardAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var contest: ViewGroup
    private lateinit var binding: FragmentHomeBinding




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
        val homeViewModel: HomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        if(activity != null){


            //<include layout="@layout/topbar"/>
            setRecyclerView(activity)

        } else {
            Log.e("HomeFragment", "Activity is null")
        }
    }

    private fun setRecyclerView(act: Activity) {
        recyclerView = act.findViewById(R.id.recycler_view)
        val listener: OnItemListener = this
        adapter = CardAdapter(listener, gameItems, act)
        val gridLayout = GridLayoutManager(activity, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayout
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val act: Activity? = activity
        if(act != null){

            //val bundle = Bundle()
            //bundle.putString("game_cover", gameItems[position].imageResource) // put image data in Intent
            //bundle.putString("game_title", gameItems[position].gameTitle) // put image data in Intent

            val intent = Intent(context, GameDetActivity::class.java)
            intent.putExtra("game_cover", gameItems[position].imageResource)
            intent.putExtra("game_title", gameItems[position].gameTitle)
            this.startActivity(intent)

            /*Utilities.insertFragment(
                act as AppCompatActivity,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName,
                bundle
            )*/

        // */
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        /*val item = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            /**
             * Called when the user submits the query. This could be due to a key press on the keyboard
             * or due to pressing a submit button.
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            /**
             * Called when the query text is changed by the user.
             * @param newText the new content of the query text field.
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            override fun onQueryTextChange(newText: String): Boolean {
                //adapter.getFilter().filter(newText);
                return true
            }
        }) // */

    }
}



























