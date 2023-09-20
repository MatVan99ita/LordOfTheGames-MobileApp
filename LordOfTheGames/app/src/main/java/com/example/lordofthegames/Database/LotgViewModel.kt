package com.example.lordofthegames.Database

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.User
import kotlinx.coroutines.launch

class LotgViewModel(application: Application): AndroidViewModel(application) {


    private var repository: LotgRepo = LotgRepo(application)

    @WorkerThread
    fun addItem(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    @WorkerThread
    fun getAllGames(): LiveData<List<Game?>?> {
        return repository.getAllGames()
    }
}