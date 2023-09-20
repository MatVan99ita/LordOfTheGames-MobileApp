package com.example.lordofthegames.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.GameCategory
import com.example.lordofthegames.db_entities.GamePlatform
import com.example.lordofthegames.db_entities.Notes
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User

@Dao
interface LotgDao {

    /**
     * INSERT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAchievement(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(categories: Categories)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComment(comments: Comments)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDiscussion(discussion: Discussion)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGameCategory(gameCategory: GameCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGamePlatform(gamePlatform: GamePlatform)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlatform(platform: Platform)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    /**
     * DELETE
     */

    /**
     * UPDATE
     */

    /**
     * QUERY VARIE
     */

    @Query("SELECT * FROM game")
    fun getAllGames(): LiveData<List<Game?>?>
}