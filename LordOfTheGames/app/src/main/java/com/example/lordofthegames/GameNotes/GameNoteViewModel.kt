package com.example.lordofthegames.GameNotes

import android.app.Application
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
        val c = repository.getNotes(game_ref)

        if(c.count > 0){
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

            val n = Notes(0, game_title, "", startTime.toString(), game_ref)
            val i = repository.newNote(n)
            return n
        }

    }

    fun saveNotes(game_title: String){

    }
}