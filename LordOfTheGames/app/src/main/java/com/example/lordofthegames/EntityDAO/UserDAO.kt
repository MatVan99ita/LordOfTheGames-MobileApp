package com.example.lordofthegames.EntityDAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lordofthegames.db_entities.User

interface UserDAO {
    fun getItems(): LiveData<List<User?>?>

    suspend fun insert(item: User)

    suspend fun delete(item: User)

    suspend fun deleteAll()

}