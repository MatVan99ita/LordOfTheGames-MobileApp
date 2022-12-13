package com.example.lordofthegames

//import com.example.lordofthegames.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities.Companion.REQUEST_IMAGE_CAPTURE
import com.example.lordofthegames.ViewModel.AddViewModel
import com.example.lordofthegames.home.HomeFragment
import com.google.android.material.navigation.NavigationView


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
 *
 *
 *
 *
 *
 * #TODO:
 *   CAPIRE COME GESTIRE L'APERTURA DELLA NAVVIEW CON LA TOPBAR
 * METTERE LA BOTTOM BAR CON LE 3 ACTIVITY PER USARE L'APP
 *
 *
 *
 *
 *
 *
 *
 */




class MainActivity : AppCompatActivity() {


    private var addViewModel: AddViewModel? = null
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.main_activity_drawer)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        if (savedInstanceState == null) {
            actionBarDrawerToggle = Utilities.insertFragment(
                this,
                HomeFragment(),
                HomeFragment::class.java.simpleName, null,
                drawerLayout = drawerLayout,
                navigationView = navigationView,
                getString(R.string.app_name)
            )

        }

        Utilities.setUpToolBar(this, findViewById<Toolbar>(R.id.toolbar), getString(R.string.app_name), drawerLayout, R.menu.top_app_bar)

        /*onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                Log.e("é aperto?", drawerLayout.isDrawerOpen(GravityCompat.END).toString())
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END)
                } else {
                    finish()
                }
                //finish()
            }
        })*/

    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()

        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bundle = data!!.extras
            if (bundle != null) {
                val imageBitmap = bundle["data"] as Bitmap?
                addViewModel!!.setImageBitmap(imageBitmap!!)
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }




}