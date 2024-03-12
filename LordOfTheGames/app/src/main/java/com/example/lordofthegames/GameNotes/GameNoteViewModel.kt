package com.example.lordofthegames.GameNotes

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.Database.LotgRepo

class GameNoteViewModel(application: Application): ViewModel() {
    private var repository: LotgRepo = LotgRepo(application)

    fun getNotes(game_title: String): Cursor {
        return repository.getNotes()
    }

    fun saveNotes(game_title: String){

    }
}