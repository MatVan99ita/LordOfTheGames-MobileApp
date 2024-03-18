package com.example.lordofthegames.GameNotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Notes
import java.util.Date

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


class GameNoteViewModel(application: Application): ViewModel() {
    private var repository: LotgRepo = LotgRepo(application)

    fun getNotes(game_title: String = "", game_ref: Int): Notes {
        Log.i("LO REFFO", "$game_ref")
        val c = repository.getNotes(game_ref)

        if(c.count > 0){
            c.moveToNext()
            Log.i("LO CONTO", c.getString(2))

            return Notes(
                c.getInt(0),        //note_id"
                c.getString(1),     //title"
                c.getString(2),     //content"
                c.getString(3),     //last_modified"
                c.getInt(4),        //game_ref"
            )

        } else {
            val startTime = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parseLocalDateTime(
                    DateTime
                        .now()
                        .toString()
                )
                .toString()

            val n = Notes(0, game_title, "", startTime, game_ref)
            val i = repository.newNote(n)
            return n
        }

    }

    fun saveNotes(content: String, last_mod:String, game_ref: Int): Int{
        return repository.saveNotes(content, last_mod, game_ref)
    }
}