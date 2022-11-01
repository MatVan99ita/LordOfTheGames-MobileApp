package com.example.lordofthegames

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Games.Achievement
import com.example.lordofthegames.Games.Categories
import com.example.lordofthegames.Games.Game
import com.example.lordofthegames.Games.Notes
import com.example.lordofthegames.GridView.CustomAdapter
import com.example.lordofthegames.GridView.SecondActivity
import com.example.lordofthegames.GridView.StartGameDialogFragment

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
    var achievements: List<Achievement> = listOf(Achievement("Uccidi", "Uccidi il cattivo", "", 1, false), Achievement("Finisci", "Finisci il gioco", "", 1, false))
    var tag: List<Categories> = listOf(Categories("GDR"), Categories("FPS"))
    var games: List<Game> = listOf(
        Game("Spado spado uccidi uccidi", achievements, "ic__search_white_24", listOf(tag[0]), "", Notes("", "") ),
        Game("Sparo sparo uccidi uccidi", achievements, "ic_menu_24dp",        listOf(tag[1]), "", Notes("", "") )
    )
    var simpleGrid: GridView? = null
    var logos = intArrayOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground
    )
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grid_layout);

        setGridView(this)
    }

    private fun setGridView(mainActivity: MainActivity) {

        simpleGrid = findViewById<View>(R.id.simpleGridView) as GridView // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView
        // Create an object of CustomAdapter and set Adapter to GirdView
        val customAdapter = CustomAdapter(applicationContext, games, this)
        simpleGrid!!.adapter = customAdapter
        // implement setOnItemClickListener event on GridView
        simpleGrid!!.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                // set an Intent to Another Activity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("copertina", games[position].image) // put image data in Intent
                StartGameDialogFragment(games[position].image)
                startActivity(intent) // start Intent
            }
    }

}
