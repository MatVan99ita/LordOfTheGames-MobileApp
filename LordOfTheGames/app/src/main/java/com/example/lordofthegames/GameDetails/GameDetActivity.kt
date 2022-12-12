package com.example.lordofthegames.GameDetails

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities


class GameDetActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gamedet)
        //actionBarDrawerToggle = Utilities.setUpDrawer(findViewById(R.id.game_det_drawer), )

        val intent: Intent = intent
        val bundle = Bundle()
        val imagePath = intent.getStringExtra("game_cover").toString()
        val title = intent.getStringExtra("game_title").toString()
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("game_title", title) // put image data in Intent

        if (savedInstanceState == null) {
            actionBarDrawerToggle = Utilities.insertFragment(
                this,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName, bundle,
                drawerLayout = findViewById(R.id.game_det_drawer),
                navigationView = findViewById(R.id.nav_view),
                title.toString()
            )
        }

        val toolbar: Toolbar? = findViewById<Toolbar>(R.id.toolbar)

        toolbar?.inflateMenu(R.menu.game_det_top_bar)
        toolbar?.title = title

        toolbar?.setNavigationOnClickListener {
            //ActionBarDrawerToggle(this, findViewById(R.id.drawer_layout), R.string.belandih, R.string.besughi).syncState()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_det_top_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.gd_app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else if(item.itemId == R.id.gd_app_bar_add) {
            Utilities.insertFragment(
                this,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName, null,
                drawerLayout,
                navigationView = findViewById(R.id.nav_view),
                title.toString()
            )
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}