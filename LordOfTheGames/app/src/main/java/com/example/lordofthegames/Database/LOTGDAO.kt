package com.example.lordofthegames.Database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lordofthegames.db_entities.*
import com.example.lordofthegames.recyclerView.GameCardItem
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface LOTGDAO {
    // @Insert(onConflict = OnConflictStrategy.IGNORE)
    // fun insertAchievement(achievement: Achievement)

    // @Insert(onConflict = OnConflictStrategy.IGNORE)
    // fun insertCategory(category: GameCategory)

    // @Insert
    // fun insertComment(comments: Comments)

    @Query("SELECT * FROM game")
    fun getGames(): Flow<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(game: Game)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User)

    @Query("SELECT nickname FROM user WHERE mail=:mail")
    fun getUser(mail: String): Array<String>

    // @Insert(onConflict = OnConflictStrategy.IGNORE)
    // fun fillGame(list: MutableList<Game>)

}