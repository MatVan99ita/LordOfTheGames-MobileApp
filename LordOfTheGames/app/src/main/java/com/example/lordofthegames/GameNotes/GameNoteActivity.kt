package com.example.lordofthegames.GameNotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.GameDetails.NotesFragment
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities

class GameNoteActivity: AppCompatActivity() {

    private lateinit var noteViewModel: ViewModel
    private lateinit var editText: EditText

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        this.savedInstanceState = savedInstanceState

        setContentView(R.layout.activity_game_notes)

        drawerLayout = findViewById(R.id.setting_drawer)
        val titile = intent.getStringExtra("game_titile")

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            titile,
            drawerLayout,
            R.menu.game_notes_menu
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this,
        )

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.game_notes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.gn_top_save) {
            /*TODO: SALVATAGGIO MANUALE SUL DB*/
            true
        } else if (item.itemId == R.id.gn_top_today) {
            /*TODO: AGGIUNTA DELLA DATA CON DATE PICKER PER SEGNARE LE COSE*/
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}