package com.example.lordofthegames.EntityRepo

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.EntityDAO.UserDAO
import com.example.lordofthegames.db_entities.User


class UserRepo(private val application: Application){


    private var db: LOTGDatabase = LOTGDatabase.getDatabase(application)
    private var userDao: UserDAO = db.userDao()
    val allItems: LiveData<List<User?>?> = userDao.getItems()



    //@WorkerThread Denotes that the annotated method should only be called on a worker thread.
    //By default Room runs suspend queries off the main thread
    @WorkerThread
    suspend fun insertItem(item: User) {
        userDao.insert(item)
    }

    @WorkerThread
    suspend fun deleteItem(item: User) {
        userDao.delete(item)
    }

    @WorkerThread
    suspend fun deleteAllItems() {
        userDao.deleteAll()
    }

    @WorkerThread
    fun getAllUser(): LiveData<List<User?>?> {
        return userDao.getItems()
    }



}