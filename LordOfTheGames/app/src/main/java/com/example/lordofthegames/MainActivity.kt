package com.example.lordofthegames

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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
    val DEBUG: Int = 0

    var gameItems: List<CardItem> = listOf(
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
    )

    private val LOG_TAG = "HomeFragment"

    private var adapter: CardAdapter? = null

    private var adapter2: CustomAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var contexter: ViewGroup
    private lateinit var gridView: GridView


    val uti: Utilities = Utilities()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null && DEBUG == 0){
            uti.insertFragment(this, HomeFragment(), HomeFragment::class.java.simpleName)
        } else {
            //setGridView(this)
        }

    }*/

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        // initializing variables of grid view with their ids.
        gridView = findViewById(R.id.gameGridView)


        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = CustomAdapter(this@MainActivity, gameItems, this)

        // on below line we are setting adapter to our grid view.
        gridView.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            Toast.makeText(
                applicationContext, gameItems[position].gameTitle + " selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }





}
