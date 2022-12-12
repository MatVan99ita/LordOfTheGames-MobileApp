package com.example.lordofthegames

//import com.example.lordofthegames.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities.Companion.REQUEST_IMAGE_CAPTURE
import com.example.lordofthegames.ViewModel.AddViewModel
import com.example.lordofthegames.home.HomeFragment
import com.google.android.material.appbar.AppBarLayout
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
    private lateinit var toolbar: Toolbar


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = findViewById(R.id.main_activity_drawer)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        //Utilities.setToolBarLayout(this, findViewById<Toolbar>(R.id.top_bar_l), getString(R.string.app_name))


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
        val toolbar: Toolbar? = findViewById<Toolbar>(R.id.toolbar)
        toolbar?.inflateMenu(R.menu.top_app_bar)
        toolbar?.title = getString(R.string.app_name)

        toolbar?.setNavigationOnClickListener {
            val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.belandih, R.string.besughi)
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            drawerLayout.closeDrawers()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);

        //Utilities.setUpToolBar(this, getString(R.string.app_name))

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





}