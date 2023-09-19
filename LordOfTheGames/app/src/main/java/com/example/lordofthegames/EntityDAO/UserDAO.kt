package com.example.lordofthegames.EntityDAO

import androidx.room.*
import com.example.lordofthegames.db_entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getItems(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: User)

    @Delete
    suspend fun delete(item: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

}