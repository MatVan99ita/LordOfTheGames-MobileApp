package com.example.lordofthegames.GameNotes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities


class GameNoteActivity: AppCompatActivity() {

    private lateinit var noteViewModel: GameNoteViewModel
    private lateinit var editText: EditText

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var savedInstanceState: Bundle? = null
    private lateinit var game_title: String

    private var game_ref: Int  =-1
    private var note_id: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_notes)


        noteViewModel = GameNoteViewModel(application)
        drawerLayout = findViewById(R.id.gn_drawer)
        game_title = intent.getStringExtra("game_title").toString()
        game_ref = intent.getIntExtra("game_ref", -1)
        editText  = findViewById(R.id.gn_Insert)
        Utilities.setUpToolBar(
            this,
            findViewById(R.id.toolbar),
            game_title,
            drawerLayout,
            R.menu.game_notes_menu
        )

        actionBarDrawerToggle = Utilities.setUpDrawer(
            drawerLayout,
            navigationView = findViewById(R.id.nav_view),
            this,
        )

        assert(game_ref > 0)
        val noteContent = noteViewModel.getNotes(game_ref)


        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.game_notes_menu, menu)
        return true
    }

    @SuppressLint("Range")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.gn_top_save) {
            /*TODO: SALVATAGGIO MANUALE SUL DB*/
            noteViewModel.saveNotes(game_title)
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
                true
        } else if (item.itemId == R.id.gn_top_today) {
            /*TODO: AGGIUNTA DELLA DATA CON DATE PICKER PER SEGNARE LE COSE*/
            val dpd = DatePickerDialog(
                this
            )

            dpd.show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}