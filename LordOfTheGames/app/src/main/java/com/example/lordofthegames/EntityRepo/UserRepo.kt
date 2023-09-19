package com.example.lordofthegames.EntityRepo

import com.example.lordofthegames.EntityDAO.UserDAO

import androidx.annotation.WorkerThread
import com.example.lordofthegames.db_entities.User
import kotlinx.coroutines.flow.Flow



class UserRepo(private val userDAO: UserDAO){

    val allItems: Flow<List<User>> = userDAO.getItems()

    //@WorkerThread Denotes that the annotated method should only be called on a worker thread.
    //By default Room runs suspend queries off the main thread
    @WorkerThread
    suspend fun insertItem(item: User) {
        userDAO.insert(item)
    }

    @WorkerThread
    suspend fun deleteItem(item: User) {
        userDAO.delete(item)
    }

    @WorkerThread
    suspend fun deleteAllItems() {
        userDAO.deleteAll()
    }


}