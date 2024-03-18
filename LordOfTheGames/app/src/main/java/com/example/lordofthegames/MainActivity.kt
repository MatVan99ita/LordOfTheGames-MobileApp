package com.example.lordofthegames

//import com.example.lordofthegames.databinding.ActivityMainBinding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Settings.SettingsActivity

import com.example.lordofthegames.home.CommunityFragment
import com.example.lordofthegames.home.HomeFragment
import com.example.lordofthegames.home.HomeViewModel
import com.example.lordofthegames.home.NotificationFragment
import com.example.lordofthegames.home.mygame.MyGameListFragment
import com.example.lordofthegames.user_login.LoggedActivity
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
 * #TOD
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
    private lateinit var toolbar: Toolbar

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var homeViewModel: HomeViewModel

    // private var connectSnackBar = mutableStateOf(false)
    // private var requestingData = false
    // private lateinit var networkCallback: ConnectivityManager.NetworkCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
        // networkCallback = object : ConnectivityManager.NetworkCallback(){
        //     override fun onAvailable(network: Network) {
        //         if(requestingData){
        //             sendRequest()
        //             connectSnackBar.value = false
        //         }
        //     }
//
        //     override fun onLost(network: Network) {
        //         connectSnackBar.value=true
        //     }
        // }

        /*

        */

        Utilities.createDatabase(this)

        val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        Log.e("NOME", banana.getString("nickname", "BANANA").toString())
        Log.e("COGGHIONE", banana.getString("email", "BANANA").toString())

        if(banana.contains("Theme")){
            if(banana.getString("Theme", "NoTheme").equals("Night")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            banana.edit().putString("Theme", "Night").apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // si può startare con l'app
        /**
         * TODO: Togliere questo if per far partire l'app direttamente con la lista di giochi
         *       Mettere il login quando si clicca sui bottoni o sulle cose che richiedono un account
         *       Così da poter cazzeggiare tra i giochi senza log come anime list
         *       //Se lo sharedpref non è stato settato si parte dal login
         *       val intent = Intent(this, LoggedActivity::class.java)
         *       //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
         *       intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
         *       this.startActivity(intent)
         */



        homeViewModel = HomeViewModel(application)

        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
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
                    if(this.actualFragment !is NotificationFragment) {
                        Utilities.insertFragment(
                            this,
                            NotificationFragment(),
                            NotificationFragment::class.java.simpleName,
                            null
                        )
                        this.actualFragment = NotificationFragment()
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


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        sharedPreferences.getString("Settings", "username")?.let { Log.w("POCODIO", it) }


        Utilities.generaNotifiche(this, "Prova", "Prova prova sa sa", MainActivity::class.java.simpleName)

    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()

        return super.onSupportNavigateUp()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else if(item.itemId == R.id.nav_setting){
            val intent = Intent(this, SettingsActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            this.startActivity(intent)
            true
        } else if(item.itemId == R.id.nav_usr){
            val intent = Intent(this, LoggedActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            intent.putExtra("user_det", true)
            this.startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        //    val bundle = data!!.extras
        //    if (bundle != null) {
        //        val imageBitmap = bundle["data"] as Bitmap?
        //        //addViewModel!!.setImageBitmap(imageBitmap!!)
        //    }
        //}

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_setting -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    this.startActivity(intent)
                }
                R.id.nav_usr -> {
                    val intent = Intent(this, LoggedActivity::class.java)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    this.startActivity(intent)
                }

            }
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            //if (id == R.id.nav_setting) {
            //    val intent = Intent(this, SettingsActivity::class.java)
            //    drawerLayout.closeDrawer(GravityCompat.START)
            //    this.startActivity(intent)
            //} else if (id == R.id.nav_usr){
            //    val intent = Intent(this, LoggedActivity::class.java)
            //    drawerLayout.closeDrawer(GravityCompat.START)
            //    this.startActivity(intent)
            //}
            //This is for maintaining the behavior of the Navigation view
            //onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it
            //drawer.closeDrawer(GravityCompat.START)
            true
        }
        Log.e("CreateOPTMenu", menu.toString())
        return super.onCreateOptionsMenu(menu)
    }


    fun isOnline(): Boolean{
        val conn = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return conn?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true || conn?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }



}