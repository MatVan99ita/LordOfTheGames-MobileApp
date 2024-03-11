package com.example.lordofthegames.notes

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities

class NotesActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContentView(R.layout.activity_notes)

        val intent: Intent = intent
        val bundle = Bundle()
        val game_title = intent.getStringExtra("game_title").toString()
        bundle.putString("game_title", game_title)
        drawerLayout = findViewById(R.id.notes_drawer)

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                NotesFragment(),
                NotesFragment::class.java.simpleName, bundle,
            )
        }

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            game_title,
            drawerLayout,
            R.menu.game_det_top_bar,
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this
        )

    }
}