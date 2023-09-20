package com.example.lordofthegames.EntityDAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lordofthegames.db_entities.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getItems(): LiveData<List<User?>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: User)

    @Delete
    suspend fun delete(item: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

}