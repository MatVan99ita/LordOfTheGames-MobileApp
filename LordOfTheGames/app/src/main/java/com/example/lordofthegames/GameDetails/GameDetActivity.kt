package com.example.lordofthegames.GameDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.GameNotes.GameNoteActivity
import com.example.lordofthegames.ViewModel.GameDetViewModel2
import com.example.lordofthegames.user_login.LoggedActivity
import com.google.android.material.navigation.NavigationView


class GameDetActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var gameDetViewModel: GameDetViewModel2
    private lateinit var game_det_intent: Intent
    private lateinit var string: String
    private lateinit var navigationView: NavigationView
    private var game_ref: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gamedet)
        //actionBarDrawerToggle = Utilities.setUpDrawer(findViewById(R.id.game_det_drawer), )

        game_det_intent = intent
        val bundle = Bundle()
        val imagePath = intent.getStringExtra("game_cover").toString()
        val title = intent.getStringExtra("game_title").toString()
        gameDetViewModel = ViewModelProvider(this)[GameDetViewModel2::class.java]
        string = title
        game_ref = intent.getIntExtra("game_id", -1)
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("game_title", title) // put image data in Intent
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("email", this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("email", "sesso"))
        drawerLayout = findViewById(R.id.game_det_drawer)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName, bundle,
            )
        }

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.topbar),
            title,
            drawerLayout,
            R.menu.game_det_top_bar,
        )
        setSupportActionBar(findViewById(R.id.topbar))

        val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        navigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView,
            this,
            gameDetViewModel.getUsrImg(banana.getString("email", "BANANA")!!)

        )




    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.nav_setting) {
            val intent = Intent(this, SettingsActivity::class.java)
            this.startActivity(intent)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else if (item.itemId == R.id.gd_app_bar_note) {
            Log.i("LO REFER", "$game_ref")
            if(game_ref > 0){
                val intent = Intent(this, GameNoteActivity::class.java)
                intent.putExtra("game_title", string)
                intent.putExtra("game_id", game_ref)

                this.startActivity(intent)
            }
            true
        } else {
            super.onOptionsItemSelected(item)
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}