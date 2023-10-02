package com.example.lordofthegames.GameDetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User

class GameDetViewModel(application: Application): AbstractViewModel(application) {

    fun getGameDetail(game_name: String): LiveData<List<Game?>?> {
        return repository.getGameDetail(game_name)
    }

    fun getGamePlatform(game_title: String): LiveData<List<Platform?>?>{
        return repository.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): LiveData<List<Achievement?>?>{
        return repository.getGameAchievement(game_title)
    }

    fun getGameCategory(game_title: String): LiveData<List<Categories?>?>{
        return repository.getGameCategory(game_title)
    }



}