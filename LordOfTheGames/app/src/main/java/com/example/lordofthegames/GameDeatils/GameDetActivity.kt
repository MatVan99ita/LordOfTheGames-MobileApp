package com.example.lordofthegames.GameDeatils

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.home.HomeFragment


class GameDetActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gamedet)
        drawerLayout = findViewById(R.id.game_det_drawer)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.belandih, R.string.besughi)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent: Intent = intent
        val bundle = Bundle()
        val imagePath = intent.getStringExtra("game_cover").toString()
        val title = intent.getStringExtra("game_title").toString()
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("game_title", title) // put image data in Intent

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName, bundle
            )
        }


        /*Utilities.insertFragment(
            this,
            GameDetFragment(),
            GameDetFragment::class.java.simpleName,
            bundle
        )*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_det_top_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        } else {
            super.onOptionsItemSelected(item)
            true
        }
        return false
    }
}