package com.example.lordofthegames.GameNotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.user_login.LoggedActivity
import com.google.android.material.navigation.NavigationView
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class GameNoteActivity: AppCompatActivity() {

    private lateinit var noteViewModel: GameNoteViewModel
    private lateinit var editText: EditText

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    private lateinit var game_title: String

    private var game_ref: Int  =-1
    private var note_id: Int = -1
    private lateinit var user_ref: String

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_notes)

        val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        noteViewModel = GameNoteViewModel(application)
        drawerLayout = findViewById(R.id.gn_drawer)
        game_title = intent.getStringExtra("game_title").toString()
        game_ref = intent.getIntExtra("game_id", -1)
        editText  = findViewById(R.id.gn_Insert)
        Utilities.setUpToolBar(
            this,
            findViewById(R.id.topbar),
            game_title,
            drawerLayout,
            R.menu.game_notes_menu
        )
        setSupportActionBar(findViewById(R.id.topbar))

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this,
        )
        Utilities.setDrawerWithUser(//todo: usarla ovunque
            this.findViewById<View>(R.id.nav_view) as NavigationView,
            banana.getString("nickname", "BANANA").toString(),
            banana.getString("email", "BANANA").toString(),
            noteViewModel.getUsrImg(banana.getString("email", "BANANA").toString())
        )

        user_ref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("email", "null")!!
        val noteContent = noteViewModel.getNotes(game_title, game_ref, user_ref = user_ref)
        editText.hint = noteContent.title
        editText.setText(noteContent.content)

        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val ib = noteViewModel.saveNotes(p0.toString(), TUDEI(), game_ref, user_ref)
                if(ib > 0)
                    Utilities.showaToast(applicationContext, "Autosave Completed")
                else
                    Utilities.showaToast(applicationContext, "Error autosaving try manual")
            }

        })

        Utilities.setDrawerWithUser(
            this.findViewById<View>(R.id.nav_view) as NavigationView,
            banana.getString("nickname", "BANANA").toString(),
            banana.getString("email", "BANANA").toString(),
            noteViewModel.getUsrImg(banana.getString("email", "BANANA").toString())
        )
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.game_notes_menu, menu)
        this.findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener { menuItem ->
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
        return true
    }

    @SuppressLint("Range")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.gn_top_save) {
            val i = noteViewModel.saveNotes(editText.text.toString(), TUDEI(), game_ref, user_ref)
            if(i > 0)
                Utilities.showaToast(applicationContext, "Save completed")
            else
                Utilities.showaToast(applicationContext, "Data save error")
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
                true
        } else if (item.itemId == R.id.gn_top_today) {
            //Aggiunge la data cib l'ora per poter segnare come se fosse un titolo
            editText.append("\n~ ${TUDEI()}:\n")
            true
        } else if(item.itemId == R.id.nav_setting) {
            val intent = Intent(this, SettingsActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            this.startActivity(intent)
            true
        } else if(item.itemId == R.id.nav_usr) {
            val intent = Intent(this, LoggedActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
            intent.putExtra("user_ref", true)
            this.startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    fun TUDEI(): String {
        val d = DateTimeFormat
            .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            //.forPattern("dd/MM/yyyy'T'HH:mm:ssZ")
            .parseLocalDateTime(
                DateTime
                    .now()
                    .toString()
            )

        return "${d.dayOfMonth}/${d.monthOfYear}/${d.year} - ${d.hourOfDay+1}:${d.minuteOfHour}:${d.secondOfMinute}"
    }





}