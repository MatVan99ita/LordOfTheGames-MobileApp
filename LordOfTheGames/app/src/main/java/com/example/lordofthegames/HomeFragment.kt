package com.example.lordofthegames

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.games.Achievement
import com.example.lordofthegames.games.Categories
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.games.Notes

import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CardItem


class HomeFragment: Fragment() {

    var gameItems: List<CardItem> = listOf(
        CardItem("ic__search_white_24", "Spado spado uccidi uccidi",         ),
        CardItem("ic_menu_24dp",        "Sparo sparo uccidi uccidi",         ),
        CardItem("ic_t_pose",           "Matel Gear Rising: Revengence",     ),
        CardItem("ic_t_pose",           "Dark Souls 3",                      ),
        CardItem("ic_t_pose",           "MARVEL Spider-Man",                 ),
        CardItem("ic_t_pose",           "Bloodborne",                        ),
        CardItem("ic_t_pose",           "God of War: Ragnarok",              ),
        CardItem("ic_t_pose",           "Gabibbo",                           ), // */
        CardItem("yee",                 "Dark Souls 3",                      ),
        CardItem("yee",                 "MARVEL Spider-Man",                 ),
        CardItem("gabibbo",             "Dark Souls 3",                      ),
        CardItem("gabibbo",             "MARVEL Spider-Man",                 ),
        CardItem("gabibbo",             "Horizon Zero Dawn: Forbidden West", ),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",             ),
        CardItem("gabibbo",             "Dark Souls 3",                      ),
        CardItem("yee",                 "MARVEL Spider-Man",                 ),
        CardItem("gabibbo",             "Bloodborne",                        ),
        CardItem("gabibbo",             "Dark Souls 3",                      ),
        CardItem("yee",                 "MARVEL Spider-Man",                 ),
        CardItem("gabibbo",             "Bloodborne",                        ),
    )

    private val LOG_TAG = "HomeFragment"

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
        var activity: FragmentActivity? = activity

        if(activity != null) {
            setRecyclerView(activity)
        } else {
            Log.e(LOG_TAG, "Activity is null")
        }
    }

    private fun setRecyclerView(activity: Activity) {
        recyclerView = activity.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        this.adapter = CardAdapter(gameItems, activity)
        recyclerView.adapter = adapter

    }
}



























