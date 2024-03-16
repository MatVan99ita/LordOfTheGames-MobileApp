package com.example.lordofthegames.GameNotes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType.dayOfMonth
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


    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_notes)


        noteViewModel = GameNoteViewModel(application)
        drawerLayout = findViewById(R.id.gn_drawer)
        game_title = intent.getStringExtra("game_title").toString()
        game_ref = intent.getIntExtra("game_id", -1)
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


        val noteContent = noteViewModel.getNotes(game_title, game_ref)
        editText.hint = noteContent.title
        editText.setText(noteContent.content)

        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val ib = noteViewModel.saveNotes(p0.toString(), TUDEI(), game_ref)
                if(ib > 0)
                    Utilities.showaToast(applicationContext, "Autosave Completed")
                else
                    Utilities.showaToast(applicationContext, "Error autosaving try manual")
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
            val i = noteViewModel.saveNotes(editText.text.toString(), TUDEI(), game_ref)
            if(i > 0)
                Utilities.showaToast(applicationContext, "Save completed")
            else
                Utilities.showaToast(applicationContext, "Data save error")
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)){
                true
        } else if (item.itemId == R.id.gn_top_today) {
            /*AGGIUNTA DELLA DATA CON DATE PICKER PER SEGNARE LE COSE, si ma no*/
            //val dpd = DatePickerDialog(
            //    this
            //)
            //dpd.show()

            editText.append(TUDEI())

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