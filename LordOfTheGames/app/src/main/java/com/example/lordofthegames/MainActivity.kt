package com.example.lordofthegames

//import com.example.lordofthegames.databinding.ActivityMainBinding

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities.Companion.REQUEST_IMAGE_CAPTURE
//import com.example.lordofthegames.ViewModel.AddViewModel
import com.example.lordofthegames.home.CommunityFragment
import com.example.lordofthegames.home.HomeFragment
import com.example.lordofthegames.home.SearchFragment
import com.example.lordofthegames.home.mygame.MyGameListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
 * QUERY PER IL COUNT DEGLI ACHIEVEMENT
 * SELECT
 *  (SELECT COUNT(id) FROM achievement WHERE completed != 0) as "Completati",
 *  (SELECT count(id) FROM achievement WHERE completed = 0) as "Non Completati",
 *   COUNT(id) as "Totali"
 * FROM achievement;
 *
 *
 */




class MainActivity : AppCompatActivity() {


    //private var addViewModel: AddViewModel? = null
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var actualFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.main_activity_drawer)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottom)

        actualFragment = HomeFragment()

        if (savedInstanceState == null) {
             Utilities.insertFragment(
                this,
                HomeFragment(),
                HomeFragment::class.java.simpleName, null,
            )
        }

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            getString(R.string.app_name),
            drawerLayout,
            null
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView,
            this
        )

        val drawable: Drawable? = ContextCompat.getDrawable(this, this.resources.getIdentifier("ic_gabibbo2_round", "mipmap", this.packageName))
        //val bitmap = (drawable as BitmapDrawable).bitmap
        val cianni = Utilities.drawableToBitmap(drawable!!)
        val newdrawable: Drawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(cianni!! , 100, 100, true))
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(newdrawable)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(newdrawable)
        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    if(actualFragment !is HomeFragment) {
                        Utilities.insertFragment(
                            this,
                            HomeFragment(),
                            HomeFragment::class.java.simpleName, null
                        )
                        actualFragment = HomeFragment()
                        true
                    } else {
                        Log.e("Bottom", "Home already initialized")
                        false
                    }
                }
                R.id.bottom_nav_search -> {
                    if(this.actualFragment !is SearchFragment) {
                        Utilities.insertFragment(
                            this,
                            SearchFragment(),
                            SearchFragment::class.java.simpleName,
                            null
                        )
                        this.actualFragment = SearchFragment()
                        true
                    } else {
                        Log.e("Bottom", "Search already initialized")
                        false
                    }
                }
                R.id.bottom_my_game_list -> {
                    if(this.actualFragment !is MyGameListFragment) {
                        Utilities.insertFragment(
                            this,
                            MyGameListFragment(),
                            MyGameListFragment::class.java.simpleName,
                            null
                        )
                        this.actualFragment = MyGameListFragment()
                        true
                    } else {
                        Log.e("Bottom", "Game list already initialized")
                        false
                    }
                }
                R.id.bottom_community -> {
                    if(this.actualFragment !is CommunityFragment) {
                        Utilities.insertFragment(
                            this,
                            CommunityFragment(),
                            CommunityFragment::class.java.simpleName,
                            null
                        )
                        this.actualFragment = CommunityFragment()
                        true
                    } else {
                        Log.e("Bottom", "Game list already initialized")
                        false
                    }
                }
                else -> {false}
            }
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()

        return super.onSupportNavigateUp()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else if(item.itemId == R.id.nav_setting){
            val intent = Intent(this, SettingsActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            this.startActivity(intent)
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
                //addViewModel!!.setImageBitmap(imageBitmap!!)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id == R.id.nav_setting) {
                val intent = Intent(this, SettingsActivity::class.java)
                drawerLayout.closeDrawer(GravityCompat.START)
                this.startActivity(intent)
            }
            //This is for maintaining the behavior of the Navigation view
            //onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it
            //drawer.closeDrawer(GravityCompat.START)
            true
        }
        Log.e("CreateOPTMenu", menu.toString())
        return super.onCreateOptionsMenu(menu)
    }





}