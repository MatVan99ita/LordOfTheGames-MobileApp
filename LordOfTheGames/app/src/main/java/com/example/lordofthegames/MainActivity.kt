package com.example.lordofthegames

//import com.example.lordofthegames.databinding.ActivityMainBinding
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.SideMenu.SideMenuFragment
import com.example.lordofthegames.Utilities.Companion.REQUEST_IMAGE_CAPTURE
import com.example.lordofthegames.ViewModel.AddViewModel
import com.example.lordofthegames.home.HomeFragment


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


    private var addViewModel: AddViewModel? = null
    //private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                HomeFragment(),
                HomeFragment::class.java.simpleName, null
            )
        }

        Utilities.setUpToolBar(this, getString(R.string.app_name))

        //Utilities.setUpDrawer(findViewById(R.id.main_activity_drawer), this, supportActionBar)

        drawerLayout = findViewById(R.id.main_activity_drawer)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.belandih, R.string.besughi)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val drawer: DrawerLayout = binding.drawerLayout
        //val navigationView: NavigationView = binding.navView

        //Utilities.setUpDrawer(this, drawer, navigationView)

        //addViewModel = ViewModelProvider(this)[AddViewModel::class.java]

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_settings) {
            /*Utilities.showSideMenu(
                this,
                SideMenuFragment()
            )*/
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
            true
        }
        return false
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