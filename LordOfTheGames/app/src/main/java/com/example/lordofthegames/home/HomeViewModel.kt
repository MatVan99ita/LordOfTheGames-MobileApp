package com.example.lordofthegames.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User


class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val mText: MutableLiveData<String> = MutableLiveData<String>()
    private val repository: LotgRepo = LotgRepo(application)

    fun getText(): LiveData<String?> {
        return mText
    }

    fun getCurrentUser(user_name: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(user_name, passw)
    }

    fun getAllGameSimpleDet(game_title: String = "", order: String = "Name"): LiveData<List<Game?>?>{
        return when(order){
            "Name" -> repository.getAllGameSimpleDet(game_title)
            "Platform" -> repository.getAllGameSimpleDetP(game_title)
            "Categories" -> repository.getAllGameSimpleDetC(game_title)
            else -> repository.getAllGameSimpleDet(game_title)
        }
    }

    fun getGamePlatform(game_title: String): LiveData<List<Platform?>?>{
        return repository.getGamePlatform(game_title)
    }

    fun getGameCategory(game_title: String): LiveData<List<Categories?>?>{
        return repository.getGameCategory(game_title)
    }

    fun getAllGame(): LiveData<List<Game?>?> {
        return repository.getAllGames()
    }



}