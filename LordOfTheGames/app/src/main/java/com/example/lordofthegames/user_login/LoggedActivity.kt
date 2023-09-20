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
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities


class LoggedActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        //val db: LOTGDatabase = databaseBuilder(applicationContext, LOTGDatabase::class.java, "lotgdb").build()
        //val userViewModel by viewModels<UserViewModel> {
        //    UserViewModelFactory(repository = (application as UserApplication).repository)
        //}
        //userViewModel.addItem(User("", "", ""))
        if(this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).contains("logged")){
            val intent = Intent(this, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }

        setContentView(R.layout.activity_log)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                LogInFragment(),
                LogInFragment::class.java.simpleName, null,
            )
        }

        /*if (savedInstanceState == null) {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            if (true /*sharedPreferences.contains("logged")*/) {
                Utilities.insertFragment(
                    this,
                    LoggedInFragment(),
                    LoggedInFragment::class.java.simpleName, null,
                )
            } else {
                Utilities.insertFragment(
                    this,
                    LogInFragment(),
                    LogInFragment::class.java.simpleName, null,
                )
            }
        }


        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            "User details",
            drawerLayout,
            null,
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this,
        )

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