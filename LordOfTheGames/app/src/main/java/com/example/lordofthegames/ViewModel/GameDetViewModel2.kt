package com.example.lordofthegames.ViewModel

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform

class GameDetViewModel2(application: Application): AbstractViewModel(application) {


    fun getGameDetails(game_name: String): Game {
        return repository.getGameDetail(game_name)
    }

    fun getGamePlatform(game_title: String): Cursor {
        return repository.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): List<Achievement> {
        val a = repository.getGameAchievement(game_title)
        val l: MutableList<Achievement> = mutableListOf()
        while (a.moveToNext()) {
            l.add(
                Achievement(a.getInt(0), a.getString(1), a.getString(2), a.getString(3), a.getInt(4), a.getInt(5), a.getInt(6), a.getInt(7))
            )
        }
        return l
    }

    fun getGameCategory(game_title: String): Cursor {
        return repository.getGameCategory(game_title)
    }



}