package com.example.lordofthegames.GameNotes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.Database.LotgRepo

class GameNoteViewMode(application: Application): ViewModel() {
    private var repository: LotgRepo = LotgRepo(application)

    fun getNotes(game_title: String){

    }

    fun saveNotes(game_title: String){

    }
}