package com.example.lordofthegames.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.db_entities.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LOTGViewModel(application: Application): AndroidViewModel(application) {

    private val allData: Flow<List<Game>>
    private val repo: LOTGRepository

    init {
        val gameDAO = LOTGDatabase.getDatabase(application).lotgdao()
        repo = LOTGRepository(gameDAO)
        allData = repo.allGames
    }

    fun addGame(game: Game){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertGame(game)
        }
    }
}