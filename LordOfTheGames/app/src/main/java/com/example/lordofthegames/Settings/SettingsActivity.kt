package com.example.lordofthegames.Settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.user_login.LoggedActivity

class SettingsActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        this.setTheme(R.style.Theme_LordOfTheGames)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        drawerLayout = findViewById(R.id.setting_drawer)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                SettingsFragment(),
                SettingsFragment::class.java.simpleName, null,
            )
        } // */

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            getString(R.string.settings),
            drawerLayout,
            null,
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this,
        )

        //val btn: Button = findViewById(R.id.btn_verde).setOnClickListener(this);
        //val btn1: Button = findViewById(R.id.btn_azzurro).setOnClickListener(this);

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else
            if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else if(item.itemId == R.id.nav_home){
            val intent = Intent(this, MainActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            this.startActivity(intent)
            true
        } else if(item.itemId == R.id.nav_usr){
            val intent = Intent(this, LoggedActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            this.startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}