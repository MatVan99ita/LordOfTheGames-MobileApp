package com.example.lordofthegames.Database

import android.app.Application
import android.database.Cursor
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Pair
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

    fun getGameDetail(game_title: String): Cursor {
        return lotgDao.getGameDetail(game_title)
    }


    fun getGamePlatform(game_title: String): Cursor {
        return lotgDao.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): Cursor {
        return lotgDao.getGameAchievement(game_title)
    }

    fun getGameCategory(game_title: String): Cursor {
        return lotgDao.getGameCategory(game_title)
    }

    fun getAllGameSimpleDet(game_title: String = ""): Cursor {
        return lotgDao.getAllGameSimpleDet(game_title)
    }

    fun getAchievementCount(game_title: String): Cursor{
        return lotgDao.getAchievementCount(game_title)
    }



    fun getAllGameSimpleDetP(condition: String): Cursor {
        return lotgDao.getAllGameSimpleDetP(condition)
    }

    fun getAllGameSimpleDetC(condition: String): Cursor {
        return lotgDao.getAllGameSimpleDetC(condition)
    }


    fun addUserItem(userItem: User) {
        LOTGDatabase.executor.execute { lotgDao.insertUser(userItem) }
    }

    fun getSimp(): Cursor {
        return lotgDao.getSIMP()
    }


}