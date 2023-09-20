package com.example.lordofthegames.Database

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.User

class LotgRepo(application: Application) {


    private var db: LOTGDatabase = LOTGDatabase.getDatabase(application)
    private var lotgDao: LotgDao = db.lotgdao()
    //val allItems: LiveData<List<User?>?> = userDao.getItems()

    @WorkerThread
    fun insertUser(user: User) {
        lotgDao.insertUser(user)
    }

    @WorkerThread
    fun getAllGames(): LiveData<List<Game?>?> {
        return lotgDao.getAllGames()
    }

    fun getCurrentUser(email: String?): LiveData<List<User?>?>? {
        return lotgDao.getCurrentUser(email)
    }
}