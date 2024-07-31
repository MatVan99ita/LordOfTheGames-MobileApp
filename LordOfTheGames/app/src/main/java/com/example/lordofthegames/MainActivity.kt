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
import android.net.NetworkInfo
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

    //TODO: richiedere cose sulla rete e controllare che il dispositivo sia collegatp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Utilities.isNetworkConnected2(this)) {

            Utilities.createDatabase(this)

            val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            Log.e("NOME", banana.getString("nickname", "BANANA").toString())
            Log.e("COGGHIONE", banana.getString("email", "BANANA").toString())

            if (banana.contains("Theme")) {
                if (banana.getString("Theme", "NoTheme").equals("Night")) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            } else {
                banana.edit().putString("Theme", "Night").apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            if (!banana.contains("email") && !banana.contains("nickname") && !banana.contains("logged")) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        LoggedActivity::class.java
                    )
                )
            } else {
                homeViewModel = HomeViewModel(application)

                setContentView(R.layout.activity_main)
                toolbar = findViewById(R.id.topbar)
                drawerLayout = findViewById(R.id.main_activity_drawer)


                navigationView = findViewById(R.id.nav_view)
                bottomNavigationView = findViewById(R.id.bottom)
                actualFragment = HomeFragment()
                val bundle: Bundle = Bundle()
                bundle.putString("email", banana.getString("email", "sesso"))

                if (savedInstanceState == null) {
                    Utilities.insertFragment(
                        this,
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        bundle,
                    )
                }

                Utilities.setUpToolBar(
                    this,
                    findViewById(R.id.topbar),
                    getString(R.string.app_name),
                    drawerLayout,
                    null
                )

                setSupportActionBar(toolbar)

                actionBarDrawerToggle = Utilities.setUpDrawer(
                    drawerLayout,
                    navigationView,
                    this
                )

                val drawable: Drawable? = ContextCompat.getDrawable(
                    this,
                    this.resources.getIdentifier("ic_gabibbo2_round", "mipmap", this.packageName)
                )
                //val bitmap = (drawable as BitmapDrawable).bitmap
                val cianni = Utilities.drawableToBitmap(drawable!!)
                val newdrawable: Drawable =
                    BitmapDrawable(resources, Bitmap.createScaledBitmap(cianni!!, 100, 100, true))
                //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                //supportActionBar!!.setHomeAsUpIndicator(newdrawable)
                supportActionBar?.setDisplayHomeAsUpEnabled(true);
                supportActionBar?.setHomeAsUpIndicator(newdrawable)
                bottomNavigationView.itemIconTintList = null

                bottomNavigationView.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.bottom_nav_home -> {
                            if (actualFragment !is HomeFragment) {
                                Utilities.insertFragment(
                                    this,
                                    HomeFragment(),
                                    HomeFragment::class.java.simpleName,
                                    bundle
                                )
                                actualFragment = HomeFragment()
                                true
                            } else {
                                Log.e("Bottom", "Home already initialized")
                                false
                            }
                        }

                        R.id.bottom_nav_search -> {
                            if (this.actualFragment !is NotificationFragment) {
                                Utilities.insertFragment(
                                    this,
                                    NotificationFragment(),
                                    NotificationFragment::class.java.simpleName,
                                    bundle
                                )
                                this.actualFragment = NotificationFragment()
                                true
                            } else {
                                Log.e("Bottom", "Search already initialized")
                                false
                            }
                        }

                        R.id.bottom_my_game_list -> {
                            if (this.actualFragment !is MyGameListFragment) {
                                Utilities.insertFragment(
                                    this,
                                    MyGameListFragment(),
                                    MyGameListFragment::class.java.simpleName,
                                    bundle
                                )
                                this.actualFragment = MyGameListFragment()
                                true
                            } else {
                                Log.e("Bottom", "Game list already initialized")
                                false
                            }
                        }

                        R.id.bottom_community -> {
                            if (this.actualFragment !is CommunityFragment) {
                                Utilities.insertFragment(
                                    this,
                                    CommunityFragment(),
                                    CommunityFragment::class.java.simpleName,
                                    bundle
                                )
                                this.actualFragment = CommunityFragment()
                                true
                            } else {
                                Log.e("Bottom", "Game list already initialized")
                                false
                            }
                        }

                        else -> {
                            false
                        }
                    }
                }


                val sharedPreferences: SharedPreferences =
                    this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

                sharedPreferences.getString("Settings", "username")?.let { Log.w("POCODIO", it) }

            }
        } else {
            Utilities.showaToast(this, "FALLITOH")
        }
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
            intent.putExtra("user_ref", true)
            this.startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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