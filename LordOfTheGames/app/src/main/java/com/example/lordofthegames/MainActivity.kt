package com.example.lordofthegames

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.ViewModel.AddViewModel
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

    val DEBUG = 0

    var gameItems: List<CardItem> = listOf(
        CardItem("ic__search_white_24", "Spado spado uccidi uccidi"),
        CardItem("ic_menu_24dp", "Sparo sparo uccidi uccidi"),
        CardItem("ic_t_pose", "Matel Gear Rising: Revengence"),
        CardItem("ic_t_pose", "Dark Souls 3"),
        CardItem("ic_t_pose", "MARVEL Spider-Man"),
        CardItem("ic_t_pose", "Bloodborne"),
        CardItem("ic_t_pose", "God of War: Ragnarok"),
        CardItem("ic_t_pose", "Gabibbo"), // */
        CardItem("yee", "Dark Souls 3"),
        CardItem("yee", "MARVEL Spider-Man"),
        CardItem("gabibbo", "Dark Souls 3"),
        CardItem("gabibbo", "MARVEL Spider-Man"),
        CardItem("gabibbo", "Horizon Zero Dawn: Forbidden West"),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN"),
        CardItem("yee", "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN"),
        CardItem("yee", "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo", "Gabibbo BELAAAAAAAAAN"),
        CardItem("gabibbo", "Dark Souls 3"),
        CardItem("yee", "MARVEL Spider-Man"),
        CardItem("gabibbo", "Bloodborne"),
        CardItem("gabibbo", "Dark Souls 3"),
        CardItem("yee", "MARVEL Spider-Man"),
        CardItem("gabibbo", "Bloodborne"),
    )

    private lateinit var gridView: GridView
    private lateinit var addViewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) Utilities.insertFragment(
            this, HomeFragment(),
            HomeFragment::class.java.simpleName
        )
        addViewModel = ViewModelProvider(this)[AddViewModel::class.java]
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // return super.onCreateOptionsMenu(menu);
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            return true
        }
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Utilities.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val bundle: Bundle? = data?.extras
            if(bundle != null){
                val bitmap: Bitmap = bundle.get("data") as Bitmap
                addViewModel.setImageBitmap(bitmap)
            }
        }
    }

}
