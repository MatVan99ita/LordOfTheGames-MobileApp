package com.example.lordofthegames

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Games.Achievement
import com.example.lordofthegames.Games.Categories
import com.example.lordofthegames.Games.Game
import com.example.lordofthegames.Games.Notes
import com.example.lordofthegames.GridView.CustomAdapter
import com.example.lordofthegames.GridView.SecondActivity

class HomeFragment: Fragment() {
    private lateinit var gridView: GridView
    private lateinit var adapter: CustomAdapter

    private val LOG_TAG = "HomeFragment"

    var achievements: List<Achievement> = listOf(Achievement("Uccidi", "Uccidi il cattivo", "", 1, false), Achievement("Finisci", "Finisci il gioco", "", 1, false))
    var tag: List<Categories> = listOf(Categories("GDR"), Categories("FPS"))
    var games: List<Game> = listOf(
        Game("Spado spado uccidi uccidi",         achievements, "ic__search_white_24",  listOf(tag[0]),         "", Notes("", "") ),
        Game("Sparo sparo uccidi uccidi",         achievements, "ic_menu_24dp",         listOf(tag[1]),         "", Notes("", "") ),
        Game("Matel Gear Rising: Revengence",     achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Dark Souls 3",                      achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man",                 achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Bloodborne",                        achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("God of War: Ragnarok",              achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo",                           achievements, "ic_t_pose",            listOf(tag[1], tag[0]), "", Notes("", "") ), // */
        Game("Dark Souls 3",                      achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man",                 achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Dark Souls 3",                      achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man",                 achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Horizon Zero Dawn: Forbidden West", achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Gabibbo BELAAAAAAAAAN",             achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Dark Souls 3",                      achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man",                 achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Bloodborne",                        achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Dark Souls 3",                      achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("MARVEL Spider-Man",                 achievements, "yee",                  listOf(tag[1], tag[0]), "", Notes("", "") ),
        Game("Bloodborne",                        achievements, "gabibbo",              listOf(tag[1], tag[0]), "", Notes("", "") ),
    )

    var simpleGrid: GridView? = null
    var recyclerView: RecyclerView? = null


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activity: FragmentActivity? = activity
        if(activity != null){
            setGridView(activity)
        } else {
            Log.e(LOG_TAG, "Activity is null")
        }

    }


    private fun setGridView(activity: Activity) {

        simpleGrid = activity.findViewById(R.id.simpleGridView) as GridView // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView
        // Create an object of CustomAdapter and set Adapter to GirdView
        val customAdapter: CustomAdapter = CustomAdapter(games, activity)
        simpleGrid!!.adapter = customAdapter
        // implement setOnItemClickListener event on GridView
        /*simpleGrid!!.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                // set an Intent to Another Activity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("game_cover", games[position].image) // put image data in Intent
                intent.putExtra("game_title", games[position].name) // put image data in Intent
                startActivity(intent) // start Intent
            }*/
    }
}

