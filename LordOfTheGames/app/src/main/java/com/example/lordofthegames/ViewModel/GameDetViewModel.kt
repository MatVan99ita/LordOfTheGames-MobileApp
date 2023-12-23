package com.example.lordofthegames.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.EntityRepo.GameDetRepo
import kotlinx.coroutines.launch

class GameDetViewModel(application: Application): AndroidViewModel(
    application
) {
    private var repository: GameDetRepo = GameDetRepo(application)

    fun getGameDetails(game_title: String) = viewModelScope.launch {
        repository.getGameDetails(game_title)
    }

    fun getGameAchievement(game_title: String) = viewModelScope.launch {
        repository.getGameAchievement(game_title)
    }

    fun getGameCategories(game_title: String) = viewModelScope.launch {
        repository.getGameCategories(game_title)
    }

    fun getGamePlatform(game_title: String) = viewModelScope.launch {
        repository.getGamePlatform(game_title)
    }



}