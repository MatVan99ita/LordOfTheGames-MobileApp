package com.example.lordofthegames.Database

import android.app.Application
import android.database.Cursor
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User

class LotgRepo(application: Application) {


    private var db: LOTGDatabase = LOTGDatabase.getDatabase(application)
    private var lotgDao: LotgDao = db.lotgdao()
    //val allItems: LiveData<List<User?>?> = userDao.getItems()

    @WorkerThread
    fun insertUser(user: User): Long {
        return lotgDao.insertUser(user)
    }

    @WorkerThread
    fun getAllGames(): LiveData<List<Game?>?> {
        return lotgDao.getAllGames()
    }

    fun getCurrentUser(email: String, passw: String): LiveData<List<User?>?> {
        return lotgDao.getCurrentUser(email, passw)
    }

    fun getUserByN(nick: String): LiveData<List<User?>?> {
        return lotgDao.getUsrByNick(nick)
    }

    fun getGameDetail(game_title: String): LiveData<List<Game?>?> {
        return lotgDao.getGameDetail(game_title)
    }


    fun getGamePlatform(game_title: String): LiveData<List<Platform?>?>{
        return lotgDao.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): LiveData<List<Achievement?>?>{
        return lotgDao.getGameAchievement(game_title)
    }

    fun getGameCategory(game_title: String): LiveData<List<Categories?>?>{
        return lotgDao.getGameCategory(game_title)
    }

    fun getAllGameSimpleDet(condition: String = "", order: String = "Name"): LiveData<List<Game?>?>{
        return lotgDao.getAllGameSimpleDet(condition, order)
    }

    fun getAchievementCount(game_title: String): Cursor{
        return lotgDao.getAchievementCount(game_title)
    }

    fun gameExists(game_title: String): LiveData<Boolean?>{
        return lotgDao.gameExists(game_title)
    }

    fun getAllGameSimpleDetP(condition: String): LiveData<List<Game?>?> {
        return lotgDao.getAllGameSimpleDetP(condition)
    }

    fun getAllGameSimpleDetC(condition: String): LiveData<List<Game?>?> {
        return lotgDao.getAllGameSimpleDetC(condition)
    }


}