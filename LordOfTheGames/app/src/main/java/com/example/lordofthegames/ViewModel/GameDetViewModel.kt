package com.example.lordofthegames.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.Database.LotgRepo
import kotlinx.coroutines.launch

class GameDetViewModel(application: Application): AndroidViewModel(
    application
) {
    private var repository: LotgRepo = LotgRepo(application)

    fun getGameDetails(game_title: String) = viewModelScope.launch {
        repository.getGameDetail(game_title)
    }

    fun getGameAchievement(game_title: String) = viewModelScope.launch {
        repository.getGameAchievement(game_title)
    }

    fun getGameCategories(game_title: String) = viewModelScope.launch {
        repository.getGameCategory(game_title)
    }

    fun getGamePlatform(game_title: String) = viewModelScope.launch {
        repository.getGamePlatform(game_title)
    }



}