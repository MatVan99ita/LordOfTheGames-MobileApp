package com.example.lordofthegames

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.gridView.CustomAdapter

import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CardItem


class HomeFragment: Fragment() {

    var gameItems: ArrayList<CardItem> = listOf(
        CardItem("ic__search_white_24", "Spado spado uccidi uccidi",),
        CardItem("ic_menu_24dp", "Sparo sparo uccidi uccidi",),
        CardItem("ic_t_pose", "Matel Gear Rising: Revengence",),
        CardItem("ic_t_pose", "Dark Souls 3",),
        CardItem("ic_t_pose", "MARVEL Spider-Man",),
        CardItem("ic_t_pose", "Bloodborne",),
        CardItem("ic_t_pose", "God of War: Ragnarok",),
        CardItem("ic_t_pose", "Gabibbo",), // */
        CardItem("yee", "Dark Souls 3",),
        CardItem("yee", "MARVEL Spider-Man",),
        CardItem("gabibbo", "Dark Souls 3",),
        CardItem("gabibbo", "MARVEL Spider-Man",),
        CardItem("gabibbo", "Horizon Zero Dawn: Forbidden West",),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN",),
        CardItem("yee", "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN",),
        CardItem("yee", "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo", "Dark Souls 3",),
        CardItem("yee", "MARVEL Spider-Man",),
        CardItem("gabibbo", "Bloodborne",),
        CardItem("gabibbo", "Dark Souls 3",),
        CardItem("yee", "MARVEL Spider-Man",),
        CardItem("gabibbo", "Bloodborne",),
    ) as ArrayList<CardItem>

    private val LOG_TAG = "HomeFragment"

    private var adapter: CardAdapter? = null

    private var adapter2: CustomAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var contexter: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            this.contexter = container
        }
        return inflater.inflate(R.layout.home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activity: FragmentActivity? = activity

        if(activity != null) {
            //setRecyclerView(activity)
            setGridView(activity)
        } else {
            Log.e(LOG_TAG, "Activity is null")
        }
    }

    private fun setRecyclerView(activity: Activity) {
        recyclerView = activity.findViewById(R.id.gameGridView)
        recyclerView.setHasFixedSize(true)
        this.adapter = CardAdapter(gameItems, activity)
        recyclerView.adapter = adapter

    }

    var simpleGrid: GridView? = null
    private fun setGridView(activity: Activity) {

        simpleGrid = activity.findViewById(R.id.gameGridView) as GridView // init GridView
        //this.adapter = CardAdapter(gameItems, activity)
        this.adapter2 = CustomAdapter(contexter.context, gameItems, activity)
        // Create an object of CustomAdapter and set Adapter to GirdView
        // Create an object of CustomAdapter and set Adapter to GirdView
        simpleGrid!!.adapter = adapter2
        // implement setOnItemClickListener event on GridView
        /*simpleGrid!!.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                // set an Intent to Another Activity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("game_cover", games[position].image) // put image data in Intent
                intent.putExtra("game_title", games[position].name) // put image data in Intent
                startActivity(intent) // start Intent
            }*/
    }
}



























