package com.example.lordofthegames.Database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.GameCategory
import com.example.lordofthegames.db_entities.GamePlatform
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.db_entities.UsersAchievement
import com.example.lordofthegames.db_entities.UsersGame

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
    fun newGameAdded(usersGame: UsersGame): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGameCategory(gameCategory: GameCategory): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGamePlatform(gamePlatform: GamePlatform): Long

    @Query("INSERT INTO notes (title, content, last_modified, game_ref, user_ref) VALUES (:title, :content, :last_mod, :game_ref, :user_ref)")
    fun insertNotes(title: String, content: String, last_mod: String, game_ref: Int, user_ref: String): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlatform(platform: Platform): Long

    @Query("INSERT INTO user (mail, nickname, password, photo) VALUES (:mail, :nick, :psw, :photo)")
    fun insertUser(mail: String, nick: String, psw: String, photo: String?): Long



    @Query("INSERT INTO notification (title, content, usr_ref) VALUES (:title, :content, :user_ref)")
    fun saveNotification(title: String, content: String, user_ref: String): Long
    /**
     * DELETE
     */


    /**
     * UPDATE
     */
    @Query("UPDATE UsersGame SET game_status = :game_status WHERE game_ref = :game_id AND user_ref = :user_ref")
    fun updateGameStatus(game_status: String, game_id: Int, user_ref: String): Int


    @Query("UPDATE UsersAchievement \n" +
            "SET actual_count = :actual\n" +
            "WHERE achieve_id = :achieve_id \n" +
            "AND user_ref  = :user_ref;")
    fun updateAchievement(achieve_id: Int, actual: Int, user_ref: String): Int

    @Query("UPDATE UsersAchievement " +
            "SET status = 1, " +
            "    actual_count = (" +
            "       SELECT a.total_count " +
            "       FROM achievement a " +
            "       WHERE a.achievement_id = :achieve_id)  " +
            "WHERE achieve_id = :achieve_id " +
            "AND user_ref = :user_ref")
    fun completeAchievement(achieve_id: Int, user_ref: String): Int

    @Query("UPDATE notes \n" +
            "SET content = :content, last_modified=:lastMod \n" +
            "WHERE game_ref = :gameRef\n" +
            "AND user_ref = :user_ref")
    fun saveNotification(content: String, lastMod: String, gameRef: Int, user_ref: String): Int

    @Query("UPDATE notification \n" +
            "SET read = 1 \n" +
            "WHERE read = 0")
    fun allRead(): Int

    @Query("UPDATE notification \n" +
            "SET read = 1 \n" +
            "WHERE id = :id AND usr_ref = :user_ref")
    fun notificationRead(user_ref: String, id: Int): Int


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
            "WHERE achievement.game_ref = game.game_id\n" +
            "AND game.game_title = :game_title")
    fun getGameAchievement(game_title: String): Cursor

    @Transaction
    @Query("SELECT * " +
            "FROM UsersAchievement " +
            "WHERE achieve_id = :achieve_id " +
            "AND user_ref = :user_ref")
    fun getUserAchievementStatus(achieve_id: Int, user_ref: String): UsersAchievement

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
    @Query("SELECT ( \n"+
            "SELECT COUNT(*)\n"+
            "FROM achievement a1, game g1\n"+
            "WHERE a1.game_ref = g1.game_id\n"+
            "AND g1.game_title = :game_title\n"+
            ") As total_count,\n"+ // Il conto di tutti gli achievement di un gioco
            "(\n"+
            "SELECT COUNT(*)\n"+
            "FROM UsersAchievement ua, achievement a, game g\n"+
            "WHERE a.achievement_id = ua.achieve_id\n"+
            "AND a.game_ref = g.game_id\n"+
            "AND g.game_title = :game_title\n"+
            "AND ua.status=1\n"+
            "AND ua.user_ref = :user_ref\n"+
            ") as completed_count")// il conto di tutti gli achievement completati da un utente
    fun getAchievementCount(game_title: String, user_ref: String): Cursor

    @Query("SELECT game_cover, game_title, game_id FROM game")
    fun getSIMP(): Cursor

    @Query("UPDATE UsersGame SET game_status = :new_status WHERE game_ref = :id AND user_ref = :mail")
    fun modifyGameStatus(new_status: String, id: Int, mail: String)




    @Query("SELECT * FROM notes WHERE game_ref = :game_ref AND user_ref = :user_ref")
    fun getNotes(game_ref: Int, user_ref: String): Cursor

    @Query("SELECT * FROM notification WHERE usr_ref = :user_ref")
    fun getNotification(user_ref: String): Cursor


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
           "    d.*, \n" +
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
           "    g.game_title, d.title\n" +
           "ORDER BY \n" +
           "    d.discussion_id DESC;")
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
           "    discussion_id = :discussion_id \n" +
           "ORDER BY \n" +
           "   comment_id DESC;")
    fun selectCommentFromDiscussion(discussion_id: Int): Cursor


    @Query("SELECT * FROM discussion WHERE discussion_id = :discussionId")
    fun getDiscussion(discussionId: Int): Discussion


    @Query(" SELECT count(*)                                                   as gameNumTot,\n" +
           "(SELECT count(*) FROM UsersGame where game_status = \"playing\" and user_ref = :user_ref)        as playing,\n" +
           "(SELECT count(*) FROM UsersGame where game_status = \"Wanted to play\" and user_ref = :user_ref) as wanted,\n" +
           "(SELECT count(*) FROM UsersGame where game_status = \"Abandoned\" and user_ref = :user_ref)      as abandoned,\n" +
           "(SELECT count(*) FROM UsersGame where game_status = \"Played\" and user_ref = :user_ref)         as played\n" +
           "FROM UsersGame \n" +
           "WHERE user_ref = :user_ref")
    fun getUserStatisticsCounts(user_ref: String): Cursor

    @Query("SELECT game_id \n" +
            "FROM game " +
            "WHERE game_title = :gameTitle \n" +
            "AND game_id IN (" +
            "   SELECT game_ref " +
            "   FROM UsersGame " +
            "   WHERE user_ref = :user_ref)"
    )
    fun getGameListValidity(gameTitle: String, user_ref: String): Cursor



    @Query("SELECT game_id FROM game \n" +
    "       WHERE game_title = :game_title \n" +
    "       AND game_id IN (\n" +
            "SELECT ug.game_ref \n" +
            "FROM UsersGame ug \n" +
            "WHERE ug.user_ref = :user_ref)"
    )
    fun getAddedGameListForUser(game_title: String, user_ref: String): Cursor

    @Query( "SELECT g.game_id, g.game_title, g.game_cover, ug.game_status FROM game g, UsersGame ug \n" +
            "WHERE user_ref = :user_ref \n" +
            "AND   g.game_id = game_ref \n" +
            "ORDER BY CASE \n" +
            "WHEN ug.game_status = 'Playing' then 1 \n" +
            "WHEN ug.game_status = 'Played' then 2 \n" +
            "WHEN ug.game_status = 'Wanted to play' then 3 \n" +
            "WHEN ug.game_status = 'Abandoned' then 4 \n" +
            "END ASC, g.game_title")
    abstract fun getAllOrderedFilteredGames(user_ref: String): Cursor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsersGame(usersGame: UsersGame): Long

    @Query("SELECT game_status FROM UsersGame WHERE game_ref = :game_id AND user_ref = :user_ref")
    fun getGameStatus(game_id: Int, user_ref: String): String

    @Query("SELECT photo FROM user WHERE mail = :mail")
    fun getUserImg(mail: String): String?


    @Query("UPDATE user SET photo = :img WHERE mail = :mail ")
    fun updateUsrImg(img: String, mail: String): Int

    @Query("SELECT * FROM user WHERE mail = :mail")
    fun getUsr(mail: String): User

    @Insert(Notification::class)
    fun sendNotificaton(n: Notification): Long

    @Query("UPDATE comment\n" +
            "SET comment_like = comment_like + 1\n" +
            "WHERE comment_id = :commentId;")
    fun upComment(commentId: Int): Int

    @Query("UPDATE comment\n" +
            "SET comment_dislike = comment_dislike + 1\n" +
            "WHERE comment_id = :commentId;")
    fun downComment(commentId: Int): Int

    @Query("UPDATE comment\n" +
            "SET comment_dislike = comment_like - 1\n" +
            "WHERE comment_id = :commentId;")
    fun deUpComment(commentId: Int): Int
    @Query("UPDATE comment\n" +
            "SET comment_dislike = comment_dislike - 1\n" +
            "WHERE comment_id = :commentId;")
    fun deDownComment(commentId: Int): Int

    //INSERT INTO notification (title, content, usr_ref) VALUES (:title, :content, :user_ref)")
    @Query("INSERT INTO UsersAchievement (user_ref, achieve_id, actual_count, status) VALUES (:userRef, :achieveId, :actual, :status)")
    fun createAURecord(achieveId: Int, userRef: String, actual: Int, status: Int): Long


    @Query("SELECT EXISTS(SELECT 1 FROM UsersAchievement WHERE user_ref = :userRef AND achieve_id = :achieveId)")
    fun uaExist(userRef: String, achieveId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM UsersGame WHERE user_ref = :userRef AND game_ref = :gameId)")
    fun ugExist(userRef: String, gameId: Int): Boolean


    @Query("INSERT INTO UsersGame (user_ref, game_ref, game_status) VALUES (:user_ref, :game_id, :game_status)")
    fun ugCreate(user_ref: String, game_id: Int, game_status: String): Long

    @Query("UPDATE notification SET read = 1 WHERE usr_ref = :userRef ")
    fun readAllNotification(userRef: String): Int

    @Query("DELETE FROM notification WHERE id=:id AND usr_ref = :usrRef")
    fun deleteNotification(id: Int, usrRef: String): Int
}

