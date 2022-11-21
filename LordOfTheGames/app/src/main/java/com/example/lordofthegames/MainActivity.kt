package com.example.lordofthegames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.games.Achievement
import com.example.lordofthegames.games.Categories
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.games.Notes
import com.example.lordofthegames.gridView.CustomAdapter
import com.example.lordofthegames.gridView.SecondActivity
import com.example.lordofthegames.recyclerView.CardAdapter
import com.example.lordofthegames.recyclerView.CardItem

/* Struttura del db
 *
 * | Achievements |
 * |--------------|
 * | id           |
 * | nome         |
 * | descr        |
 * | count        |
 * | game         |
 *      N                 | Tag       |
 *      ^                 |-----------|
 *      1                 | id        |         | Categorie |
 * | Game   | 1--------N> | gioco     |         |-----------|
 * |--------|             | categoria | <N----1 | id        |
 * | id     | <N-----------------------------N> | nome      |
 * | nome   |                                   | tag       |
 * | img    |        | Note      |
 * | status |        |-----------|
 * | notes  | <1---1 | id        |
 *                   | title     |
 *                   | contenuto |
 *
 * Riassunto relazioni
 *
 *                           /  Game.id      -> Tag.game
 * Game < - > Categorie ----|
 *                           \  Categorie.id -> Tag.categorie
 *
 * Note.id -> Game.notes
 * Game.id -> Achievements.game
 *
 *
 */




class MainActivity : AppCompatActivity() {

    var gameItems: List<CardItem> = listOf(
        CardItem("ic__search_white_24", "Spado spado uccidi uccidi",),
        CardItem("ic_menu_24dp",        "Sparo sparo uccidi uccidi",),
        CardItem("ic_t_pose",           "Matel Gear Rising: Revengence",),
        CardItem("ic_t_pose",           "Dark Souls 3",),
        CardItem("ic_t_pose",           "MARVEL Spider-Man",),
        CardItem("ic_t_pose",           "Bloodborne",),
        CardItem("ic_t_pose",           "God of War: Ragnarok",),
        CardItem("ic_t_pose",           "Gabibbo",), // */
        CardItem("yee",                 "Dark Souls 3",),
        CardItem("yee",                 "MARVEL Spider-Man",),
        CardItem("gabibbo",             "Dark Souls 3",),
        CardItem("gabibbo",             "MARVEL Spider-Man",),
        CardItem("gabibbo",             "Horizon Zero Dawn: Forbidden West",),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",),
        CardItem("yee",                 "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo",             "Gabibbo BELAAAAAAAAAN",),
        CardItem("gabibbo",             "Dark Souls 3",),
        CardItem("yee",                 "MARVEL Spider-Man",),
        CardItem("gabibbo",             "Bloodborne",),
        CardItem("gabibbo",             "Dark Souls 3",),
        CardItem("yee",                 "MARVEL Spider-Man",),
        CardItem("gabibbo",             "Bloodborne",),
    )

    private lateinit var gridView: GridView


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.home)

        println("BELANDIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII: $this")

        gridView = this.findViewById(R.id.gameGridView)

        Log.e("BELANDIH", this.toString())
        val courseAdapter = CustomAdapter(this@MainActivity, gameItems, this@MainActivity)

        // on below line we are setting adapter to our grid view.
        gridView.adapter = courseAdapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                applicationContext, gameItems[position].gameTitle + " selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
