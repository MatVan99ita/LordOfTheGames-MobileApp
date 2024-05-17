package com.example.lordofthegames.Database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
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
import com.example.lordofthegames.db_entities.Notification
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

    @Query("INSERT INTO notes (title, content, last_modified, game_ref) VALUES (:title, :content, :last_mod, :game_ref)")
    fun insertNotes(title: String, content: String, last_mod: String, game_ref: Int): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlatform(platform: Platform): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotes(content: Notification): Long
    /**
     * DELETE
     */


    /**
     * UPDATE
     */
    @Query("UPDATE game SET game_status = :game_status WHERE game_title = :game_title")
    fun updateGameStatus(game_title: String, game_status: String): Int


    @Query("UPDATE achievement \n" +
            "SET actual_count = :actual\n" +
            "WHERE game_ref IN (SELECT game.game_id FROM game WHERE game.game_title = :game_ref)\n" +
            "AND achievement_id  = :id;"
    )
    fun updateAchievement(game_ref: String, id: Int, actual: Int): Int

    @Query("UPDATE achievement \n" +
            "SET status = :status\n" +
            "WHERE game_ref IN (SELECT game.game_id FROM game WHERE game.game_title = :game_ref) \n" +
            "AND achievement_id = :id"
    )
    fun completeAchievement(game_ref: String, id: Int, status: Int): Int

    @Query("UPDATE notes \n" +
            "SET content = :content, last_modified=:lastMod \n" +
            "WHERE game_ref = :gameRef")
    fun saveNotes(content: String, lastMod: String, gameRef: Int): Int

    @Query("UPDATE notification \n" +
            "SET read = 1 \n" +
            "WHERE read = 0")
    fun allRead(): Int

    @Query("UPDATE notification \n" +
            "SET read = 1 \n" +
            "WHERE id = :id")
    fun notificationRead(id: Int): Int


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
    fun getGameDetail(game_title: String): Game

    @Transaction
    @Query( "SELECT platform.*\n" +
            "FROM platform\n" +
            "JOIN gameplatform ON platform.platform_id = gameplatform.platform_ref\n" +
            "JOIN game ON gameplatform.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGamePlatform(game_title: String): Cursor


    @Transaction
    @Query("SELECT achievement.*\n" +
            "FROM achievement, game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title")
    fun getGameAchievement(game_title: String): Cursor

    @Transaction
    @Query("SELECT categories.*\n" +
            "FROM categories \n" +
            "JOIN gamecategory ON categories.category_id = gamecategory.category_ref\n" +
            "JOIN game ON gamecategory.game_ref = game.game_id\n" +
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
            "FROM achievement JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title) As total_count,\n" +
            "(SELECT COUNT(*)\n" +
            "FROM achievement JOIN game\n" +
            "ON achievement.game_ref = game.game_id\n" +
            "WHERE game.game_title = :game_title AND achievement.status=1) as completed_count")
    fun getAchievementCount(game_title: String): Cursor

    @Query("SELECT game_cover, game_title, game_id FROM game")
    fun getSIMP(): Cursor

    @Query("UPDATE game SET game_status = :new_status WHERE game_id = :id")
    fun modifyGameStatus(new_status: String, id: Int)




    @Query("SELECT * FROM notes WHERE game_ref = :game_ref")
    fun getNotes(game_ref: Int): Cursor

    @Query("SELECT * FROM notification")
    fun getNotification(): Cursor


    @Query("SELECT\n"+
    "   g.game_title AS GameTitle,\n"+
    "   COUNT(d.discussion_id) AS TotalDiscussions,\n"+
    "   COALESCE(SUM(c.comment_like - c.comment_dislike), 0) AS TotalLike\n"+
    "FROM\n"+
    "   game g\n"+
    "LEFT JOIN\n"+
    "   discussion d ON g.game_id = d.game_ref\n"+
    "LEFT JOIN\n"+
    "   comment c ON d.discussion_id = c.discussion_ref\n"+
    "GROUP BY\n"+
    "   g.game_title\n"+
    "ORDER BY TotalDiscussions DESC;")
    fun selectAllCommunity(): Cursor

    @Query("SELECT \n" +
           "    g.game_id, \n" +
           "    d.discussion_id, d.title, d.content, d.user_ref, \n" +
           "    COALESCE(SUM(c.comment_like - c.comment_dislike), 0) AS TotaleLike,\n" +
           "    COUNT(c.comment_id) AS NumeroCommenti\n" +
           "FROM \n" +
           "    game g\n" +
           "INNER JOIN \n" +
           "    discussion d ON g.game_id = d.game_ref\n" +
           "LEFT JOIN \n" +
           "    comment c ON d.discussion_id = c.discussion_ref\n" +
           "WHERE\n" +
           "    g.game_title = :game_title \n" +
           "GROUP BY \n" +
           "    g.game_title, d.title;")
    fun selectAllDiscussion(game_title: String): Cursor


    @Query("SELECT \n" +
           "    comment_id, \n" +
           "    comment.content, \n" +
           "    comment_like, \n" +
           "    comment_dislike,  \n" +
           "    comment.user_ref  \n" +
           "FROM " +
           "    comment, discussion " +
           "ON discussion_ref = discussion_id " +
           "WHERE " +
           "    discussion_id = :discussion_id")
    fun selectCommentFromDiscussion(discussion_id: Int): Cursor


    @Query("SELECT * FROM discussion WHERE discussion_id = :discussionId")
    fun getDiscussion(discussionId: Int): Discussion


    @Query(" SELECT count(*)                                                   as gameNumTot,\n" +
           "(SELECT count(*) FROM game where game_status = \"playing\")        as playing,\n" +
           "(SELECT count(*) FROM game where game_status = \"Wanted to play\") as wanted,\n" +
           "(SELECT count(*) FROM game where game_status = \"Abandoned\")      as abandoned,\n" +
           "(SELECT count(*) FROM game where game_status = \"Played\")         as played\n" +
           "FROM game \n" +
           "WHERE game_status IS NOT \"NP\"")
    fun getUserStatisticsCounts(): Cursor

    @Query("SELECT game_status FROM game WHERE game_title = :gameTitle")
    fun getGameListValidity(gameTitle: String): Cursor



    @Query("SELECT game_id FROM game \n" +
    "       WHERE game_title = :game_title \n" +
    "       AND game_id IN (\n" +
            "SELECT ug.game_ref \n" +
            "FROM UsersGame ug \n" +
            "WHERE ug.user_ref = :user_ref)"
    )
    fun getAddedGameListForUser(game_title: String, user_ref: String): Cursor

    @Query( "SELECT g.game_title, ug.game_status FROM game g, UsersGame ug \n" +
            "WHERE user_ref = :user_ref \n" +
            "AND   g.game_id = game_ref \n" +
            "ORDER BY CASE \n" +
            "WHEN ug.game_status = 'Playing' then 1 \n" +
            "WHEN ug.game_status = 'Played' then 2 \n" +
            "WHEN ug.game_status = 'Wanted to play' then 3 \n" +
            "WHEN ug.game_status = 'Abandoned' then 4 \n" +
            "END ASC, g.game_title")
    fun getAllOrderedFilteredGames(user_ref: String): List<Game>

}

