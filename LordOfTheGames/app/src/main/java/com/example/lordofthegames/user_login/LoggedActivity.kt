package com.example.lordofthegames.user_login

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room.databaseBuilder
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities


class LoggedActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    private lateinit var toolbar: Toolbar
    private lateinit var viuvve: LoggedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        val db: LOTGDatabase =
            databaseBuilder(applicationContext, LOTGDatabase::class.java, "lotgdb").build()
        //val userViewModel by viewModels<UserViewModel> {
        //    UserViewModelFactory(repository = (application as UserApplication).repository)
        //}
        //userViewModel.addItem(User("", "", ""))
        val sp = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val bundle: Bundle = Bundle()
        bundle.putString("email", sp.getString("email", "sesso"))
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

                viuvve = ViewModelProvider(this)[LoggedViewModel::class.java]
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
                val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                actionBarDrawerToggle = Utilities.setUpDrawer(
                    drawerLayout,
                    navigationView = findViewById(R.id.nav_view),
                    this,
                    viuvve.getUsrImg(banana.getString("email", "BANANA")!!)
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

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}