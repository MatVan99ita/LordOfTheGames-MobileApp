package com.example.lordofthegames.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    fun insertAchievement(achievement: Achievement): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(categories: Categories): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComment(comments: Comments): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDiscussion(discussion: Discussion): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(game: Game): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGameCategory(gameCategory: GameCategory): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGamePlatform(gamePlatform: GamePlatform): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlatform(platform: Platform): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long

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

    @Query("SELECT * FROM user WHERE mail = :email AND password = :passw")
    fun getCurrentUser(email: String, passw: String): LiveData<List<User?>?>

    @Query("SELECT * FROM user WHERE nickname = :nick")
    fun getUsrByNick(nick: String): LiveData<List<User?>?>


    @Query("SELECT * FROM game WHERE game_title = :game_title")
    fun getGameDetail(game_title: String): LiveData<List<Game?>?>

    @Query("SELECT platform.*\n" +
            "FROM platform INNER JOIN gameplatform\n" +
            "ON platform.platform_id = gameplatform.platform_ref\n" +
            "INNER JOIN game\n" +
            "ON gameplatform.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGamePlatform(game_title: String): LiveData<List<Platform?>?>


    @Query("SELECT achievement.*\n" +
            "FROM achievement INNER JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGameAchievement(game_title: String): LiveData<List<Achievement?>?>


    @Query("SELECT categories.*\n" +
            "FROM categories INNER JOIN gamecategory\n" +
            "ON categories.category_id = gamecategory.category_ref\n" +
            "INNER JOIN game\n" +
            "ON gamecategory.game_ref = game.game_id\n" +
            "WHERE Game.game_title = :game_title")
    fun getGameCategory(game_title: String): LiveData<List<Categories?>?>













}