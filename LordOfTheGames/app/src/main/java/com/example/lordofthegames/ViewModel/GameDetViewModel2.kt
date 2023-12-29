package com.example.lordofthegames.ViewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform

class GameDetViewModel2(application: Application): AbstractViewModel(application) {

    fun getGameDetails(game_name: String): Cursor {
        return repository.getGameDetail(game_name)
    }

    fun getGamePlatform(game_title: String): Cursor {
        return repository.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): Cursor {
        return repository.getGameAchievement(game_title)
    }

    fun getGameCategory(game_title: String): Cursor {
        return repository.getGameCategory(game_title)
    }



}