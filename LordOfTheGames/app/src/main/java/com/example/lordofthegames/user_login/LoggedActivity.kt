package com.example.lordofthegames.user_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.room.Room.databaseBuilder
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class LoggedActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    private lateinit var toolbar: Toolbar

    /**TODO:
        *
     *  DALLE SPECIFICHE: mappa utente - grafico andamento - achievement
        *
     *  -> achievement -> tudus userbadge
     *  -> il grafico con i giochi c'è
     *  -> la mappa dell'utente adesso non lo soos se la voglio mettere
     **/




    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        val db: LOTGDatabase = databaseBuilder(applicationContext, LOTGDatabase::class.java, "lotgdb").build()
        //val userViewModel by viewModels<UserViewModel> {
        //    UserViewModelFactory(repository = (application as UserApplication).repository)
        //}
        //userViewModel.addItem(User("", "", ""))
        val sp = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val bundle: Bundle = Bundle()

        Log.i("PORCACCIODDIO", "${sp.all}")
        Log.i("PORCACCIODDIO", "${sp.getString("email", "sesso")}")

        bundle.putString("email", sp.getString("email", "sesso"))
        Log.i("PORCACCIODDIO", bundle.getString("email", "sesso"))
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                LogInFragment(),
                LogInFragment::class.java.simpleName,
                bundle,
            )
        }

        if (savedInstanceState == null) {
            //val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            if (sp.contains("logged") && sp.contains("email") && sp.contains("nickname")) {


                toolbar = findViewById(R.id.topbar)
                drawerLayout = findViewById(R.id.logged_activity_drawer)

                Utilities.insertFragment(
                    this,
                    LoggedInFragment(),
                    LoggedInFragment::class.java.simpleName,
                    bundle,
                )

                Utilities.setUpToolBar(
                    this,
                    findViewById(R.id.topbar),
                    "Equipment",
                    drawerLayout,
                    null,
                )
                setSupportActionBar(findViewById(R.id.topbar))

                actionBarDrawerToggle = Utilities.setUpDrawer(
                    drawerLayout,
                    navigationView = findViewById(R.id.nav_view),
                    this,
                )

            } else {
                Utilities.insertFragment(
                    this,
                    LogInFragment(),
                    LogInFragment::class.java.simpleName,
                    bundle,
                )
            }
        }




        //val btn: Button = findViewById(R.id.btn_verde).setOnClickListener(this);
        //val btn1: Button = findViewById(R.id.btn_azzurro).setOnClickListener(this);*/

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }
/*
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}