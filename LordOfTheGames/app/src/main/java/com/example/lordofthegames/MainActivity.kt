package com.example.lordofthegames

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.games.Achievement
import com.example.lordofthegames.games.Categories
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.games.Notes
import com.example.lordofthegames.gridView.CustomAdapter
import com.example.lordofthegames.gridView.SecondActivity
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
    val DEBUG: Int = 0

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


    val uti: Utilities = Utilities()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null && DEBUG == 0){
            uti.insertFragment(this, HomeFragment(), HomeFragment::class.java.simpleName)
        } else {
            setGridView(this)
        }

    }

    var simpleGrid: GridView? = null
    var recyclerView: RecyclerView? = null
    public fun setGridView(mainActivity: MainActivity) {

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
                intent.putExtra("game_cover", games[position].image) // put image data in Intent
                intent.putExtra("game_title", games[position].name) // put image data in Intent
                startActivity(intent) // start Intent
            }
    }



}
