package com.example.lordofthegames.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.Pair
import com.example.lordofthegames.recyclerView.GameCardItem


class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val mText: MutableLiveData<String> = MutableLiveData<String>()
    private val repository: LotgRepo = LotgRepo(application)

    fun getCurrentUser(user_name: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(user_name, passw)
    }

    fun getAllGameSimpleDet(game_title: String = "", order: String = "Name"): LiveData<List<Game>>{
        return when(order){
            "Name" -> repository.getAllGameSimpleDet("%${game_title}%")
            "Platform" -> repository.getAllGameSimpleDetP("%${game_title}%")
            "Categories" -> repository.getAllGameSimpleDetC("%${game_title}%")
            else -> repository.getAllGameSimpleDet("%${game_title}%")
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

    fun getAchievementCount(game_title: String): Pair<Int, Int> {
        return repository.getAchievementCount(game_title)
    }

    fun getSIMP(): List<GameCardItem> {
        val l = repository.getSimp()
        Log.w("POCODIO1", l.columnCount.toString())
        Log.w("POCODIO2", "${l.getColumnName(0)} - ${l.getColumnName(1)}")
        Log.w("POCODIO3", l.count.toString())
        val s = mutableListOf<GameCardItem>()


        while (l.moveToNext()){
            s.add(GameCardItem(l.getString(0).toString(), l.getString(1).toString()))
        }
        s.forEach{el -> Log.w("POCODIO4", el.toString())}

        return s
    }

}