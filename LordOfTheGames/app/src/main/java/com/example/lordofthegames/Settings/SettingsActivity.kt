package com.example.lordofthegames.Settings

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities


class SettingsActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
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
            "null"
        )
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
            super.getOnBackPressedDispatcher()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}