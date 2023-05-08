package com.example.lordofthegames.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/*
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
// */
class LOTGViewModel(private val repository: LOTGRepository): ViewModel() {

    val allItems = repository.allGames

    fun addItem(item: Game) = viewModelScope.launch {
        repository.insertGame(item)
    }

    fun addUser(user: User) =viewModelScope.launch {
        repository.insertUser(user)
    }
/*
    fun deleteItem(item: Game) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun clearItems() = viewModelScope.launch {
        repository.deleteAllItems()
    }
// */
}

class LOTGViewModelFactory(private val repository: LOTGRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LOTGViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LOTGViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}