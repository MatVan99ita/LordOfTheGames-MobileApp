package com.example.lordofthegames

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.gridView.CustomAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity
        if(activity != null){
            Utilities.setUpToolBar(activity as AppCompatActivity, getString(R.string.search))

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
            Utilities.insertFragment(act as AppCompatActivity, GameDetFragment(), GameDetFragment::class.java.simpleName)
        }
    }
}



























