package com.example.lordofthegames.Database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.lordofthegames.Pair
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

    @Transaction
    @Query("SELECT * FROM game")
    fun getAllGames(): LiveData<List<Game?>?>

    @Transaction
    @Query("SELECT * FROM user WHERE mail = :email AND password = :passw")
    fun getCurrentUser(email: String, passw: String): LiveData<List<User?>?>

    @Transaction
    @Query("SELECT * FROM user WHERE nickname = :nick")
    fun getUsrByNick(nick: String): LiveData<List<User?>?>

    @Transaction
    @Query("SELECT * FROM game WHERE game_title = :game_title")
    fun getGameDetail(game_title: String): Cursor

    @Transaction
    @Query( "SELECT platform.*\n" +
            "FROM platform INNER JOIN gameplatform\n" +
            "ON platform.platform_id = gameplatform.platform_ref\n" +
            "INNER JOIN game\n" +
            "ON gameplatform.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGamePlatform(game_title: String): Cursor


    @Transaction
    @Query("SELECT achievement.*\n" +
            "FROM achievement INNER JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGameAchievement(game_title: String): Cursor

    @Transaction
    @Query("SELECT categories.*\n" +
            "FROM categories INNER JOIN gamecategory\n" +
            "ON categories.category_id = gamecategory.category_ref\n" +
            "INNER JOIN game\n" +
            "ON gamecategory.game_ref = game.game_id\n" +
            "WHERE Game.game_title = :game_title")
    fun getGameCategory(game_title: String): Cursor

    @Transaction
    @Query("SELECT * FROM game WHERE game_title LIKE '%' || :game_title || '%' ORDER BY game_title DESC")
    fun getAllGameSimpleDet(game_title: String = ""): Cursor


    @MapInfo(keyColumn = "game_title", valueColumn = "category_name")
    @Query("SELECT DISTINCT game.game_title, categories.category_name\n" +
            "FROM categories, gamecategory, game\n" +
            "WHERE categories.category_id = gamecategory.category_ref\n" +
            "AND gamecategory.game_ref = game.game_id\n")
    fun getAllGameCategories(): LiveData<Map<String, String>>

    @MapInfo(keyColumn = "game_title")
    @Query("SELECT DISTINCT game.game_title, platform.*\n" +
            "FROM platform, gameplatform, game\n" +
            "WHERE platform.platform_id = gameplatform.platform_ref\n" +
            "AND gameplatform.game_ref = game.game_id\n")
    fun getAllGamePlatforms(): LiveData<Map<String, Platform>>



    //@MapInfo(keyColumn = "game_title")
    //@Query("SELECT game.game_title, (" +
    //"SELECT COUNT(*)\n" +
    //"FROM achievement, game\n" +
    //"WHERE achievement.game_ref = game.game_id\n) As total_count, \n" +
    //"(SELECT COUNT(*)\n" +
    //"FROM achievement, game\n" +
    //"WHERE achievement.game_ref = game.game_id\n"+
    //"AND achievement.status=1) as completed_count FROM game")
    //fun getAllGameAchievementCounts(): LiveData<Map<String, Cursor>>


    @Transaction
    @Query( "SELECT * FROM platform \n" +
            "INNER JOIN gameplatform ON platform.platform_id = gameplatform.platform_ref " +
            "INNER JOIN game ON gameplatform.game_ref = game.game_id " +
            "WHERE game.game_title LIKE '%' || :game_title || '%' " +
            "GROUP BY game.game_title " +
            "ORDER BY platform.nome;")
    fun getAllGameSimpleDetP(game_title: String): Cursor


    @Query("SELECT game_title FROM game")
    fun getGameSimpleDet(): LiveData<List<String>>

    @Transaction
    @Query("SELECT * \n" +
            "FROM categories INNER JOIN gamecategory\n" +
            "ON categories.category_id = gamecategory.category_ref\n" +
            "INNER JOIN game\n" +
            "ON gamecategory.game_ref = game.game_id\n" +
            "WHERE Game.game_title LIKE '%' || :game_title || '%' \n" +
            "GROUP BY Game.game_title \n" +
            "ORDER BY category_name")
    fun getAllGameSimpleDetC(game_title: String): Cursor

    @Transaction
    @Query("SELECT (\n" +
            "SELECT COUNT(*)\n" +
            "FROM achievement INNER JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title) As total_count,\n" +
            "(SELECT COUNT(*)\n" +
            "FROM achievement INNER JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title AND achievement.status=1) as completed_count")
    fun getAchievementCount(game_title: String): Cursor

    @Query("SELECT game_cover, game_title, game_id FROM game")
    fun getSIMP(): Cursor

    @Query("UPDATE game SET game_status = :new_status WHERE game_id = :id")
    fun modifyGameStatus(new_status: String, id: Int)

    @Query("SELECT * FROM game WHERE game_status != \"NP\" ")
    fun getAllFilteredGame(): List<Game>


}