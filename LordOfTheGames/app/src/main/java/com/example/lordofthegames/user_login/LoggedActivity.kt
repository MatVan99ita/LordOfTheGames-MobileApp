package com.example.lordofthegames.user_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
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

    /**TODO: ggiungere a login e signin il ritorno alla sezione scelta, se è solo per vedere
     *  i dettagli si torna sui dettagli dell'utente
     *  quando si è nel game list e si clicca sul bottone di edit del gioco
     *  se non c'è accesso bisogna farlo fare e mettere un bundle all'activity sull'activity precedente
     *  se si va sulla lista perosnale senza aver eseguito l'accesso si va al login diretto
     *  e se si torna indoetro bisogna rimettere la home base
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        //val db: LOTGDatabase = databaseBuilder(applicationContext, LOTGDatabase::class.java, "lotgdb").build()
        //val userViewModel by viewModels<UserViewModel> {
        //    UserViewModelFactory(repository = (application as UserApplication).repository)
        //}
        //userViewModel.addItem(User("", "", ""))
        val sp = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                LogInFragment(),
                LogInFragment::class.java.simpleName, null,
            )
        }

        if (savedInstanceState == null) {
            //val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            if (sp.contains("logged") && sp.contains("email") && sp.contains("nickname")) {


                toolbar = findViewById(R.id.toolbar)
                drawerLayout = findViewById(R.id.logged_activity_drawer)

                Utilities.insertFragment(
                    this,
                    LoggedInFragment(),
                    LoggedInFragment::class.java.simpleName, null,
                )

                Utilities.setUpToolBar(
                    this,
                    findViewById(R.id.toolbar),
                    "Equipment",
                    drawerLayout,
                    null,
                )

                actionBarDrawerToggle = Utilities.setUpDrawer(
                    drawerLayout,
                    navigationView = findViewById(R.id.nav_view),
                    this,
                )

            } else {
                Utilities.insertFragment(
                    this,
                    LogInFragment(),
                    LogInFragment::class.java.simpleName, null,
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