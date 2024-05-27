package com.example.lordofthegames.Database

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Notes
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.db_entities.UsersAchievement
import com.example.lordofthegames.db_entities.UsersGame

class LotgRepo(application: Application) {

    private var db: LOTGDatabase = LOTGDatabase.getDatabase(application)
    private var lotgDao: LotgDao = db.lotgdao()
    //val allItems: LiveData<List<User?>?> = userDao.getItems()

    @WorkerThread
    fun insertUser(user: User): Long {
        return lotgDao.insertUser(user)
    }

    @WorkerThread
    fun getAllGames(): LiveData<List<Game?>?> {
        return lotgDao.getAllGames()
    }

    fun getCurrentUser(email: String, passw: String): LiveData<List<User?>?> {
        return lotgDao.getCurrentUser(email, passw)
    }

    fun getUserByN(nick: String): LiveData<List<User?>?> {
        return lotgDao.getUsrByNick(nick)
    }

    fun getGameDetail(game_title: String): Game {
        return lotgDao.getGameDetail(game_title)
    }


    fun getGamePlatform(game_title: String): Cursor {
        return lotgDao.getGamePlatform(game_title)
    }

    fun getGameAchievement(game_title: String): Cursor {
        return lotgDao.getGameAchievement(game_title)
    }

    fun getGameCategory(game_title: String): Cursor {
        return lotgDao.getGameCategory(game_title)
    }

    fun getAllGameSimpleDet(game_title: String = ""): Cursor {
        return lotgDao.getAllGameSimpleDet(game_title)
    }

    fun getGameSimpleDet(): LiveData<List<String>> {
        return lotgDao.getGameSimpleDet()
    }

    fun getAchievementCount(game_title: String, user_ref: String): Cursor{
        return lotgDao.getAchievementCount(game_title, user_ref)
    }



    fun getAllGameSimpleDetP(condition: String): Cursor {
        return lotgDao.getAllGameSimpleDetP(condition)
    }

    fun getAllGameSimpleDetC(condition: String): Cursor {
        return lotgDao.getAllGameSimpleDetC(condition)
    }


    fun addUserItem(userItem: User) {
        LOTGDatabase.executor.execute { lotgDao.insertUser(userItem) }
    }

    fun getSimp(): Cursor {
        return lotgDao.getSIMP()
    }

    fun modifyGameStatus(stat: String, id: Int, mail: String){
        lotgDao.modifyGameStatus(stat, id, mail)
    }


    fun getOrderedFilt(user_ref: String): Cursor {
        return lotgDao.getAllOrderedFilteredGames(user_ref)
    }

    fun updateGameStatus(game_status: String, game_id: Int, user_ref: String): Int {
        return lotgDao.updateGameStatus(game_status, game_id, user_ref)
    }

    fun updateAchievement(achieve_id: Int, actual: Int, user_ref: String): Int {
        return lotgDao.updateAchievement(achieve_id, actual, user_ref)
    }
    fun completeAchievement(achieve_id: Int, user_ref: String): Int {
        return lotgDao.completeAchievement(achieve_id, user_ref)
    }

    fun getNotes(game_ref: Int): Cursor {
        return lotgDao.getNotes(game_ref)
    }

    fun newNote(notes: Notes): Long {
        Log.i("LO NOTO", notes.toString())
        return lotgDao.insertNotes(notes.title, notes.content, notes.last_modified, notes.game_ref)
    }

    fun saveNotes(content: String, lastMod: String, gameRef: Int): Int {
        return lotgDao.saveNotes(content, lastMod, gameRef)
    }

    fun saveNotes(content: Notification): Long {
        return lotgDao.saveNotes(content)
    }

    fun allRead(): Int {
        return lotgDao.allRead()
    }

    fun notificationRead(id: Int): Int {
        return lotgDao.notificationRead(id)
    }

    fun getNotification(): Cursor {
        return lotgDao.getNotification()
    }


    /** Cursor(GameTitle, TotalDiscussion, TotalLike) */
    fun selectAllCommunity(): Cursor{
        return lotgDao.selectAllCommunity()
    }

    /**Cursor(discussion_id, title, content, TotalLike, NumeroCommenti*/
    fun selectAllDiscussion(game_title: String): Cursor{
        return lotgDao.selectAllDiscussion(game_title)
    }

    /**Cursor(comment_id, content, comment_like, comment_dislike)*/
    fun selectCommentFromDiscussion(discussion_id: Int): Cursor{
        return lotgDao.selectCommentFromDiscussion(discussion_id)
    }

    fun getDiscussionSpecific(discussionId: Int): Discussion {
        return lotgDao.getDiscussion(discussionId)
    }

    /** gameNumTot, playing, wanted, abandoned, played*/
    fun getUserStatisticsCounts(user_ref: String): Cursor{
        return lotgDao.getUserStatisticsCounts(user_ref)
    }

    fun getGameListValidity(gameTitle: String, user_ref: String): Cursor {
        return lotgDao.getGameListValidity(gameTitle, user_ref)
    }

    fun getFilt(user_ref: String): Cursor {
        return lotgDao.getAllOrderedFilteredGames(user_ref)
    }

    fun  insertUsersGame(usersGame: UsersGame): Long{
        return lotgDao.insertUsersGame(usersGame)
    }

    fun getGameStatus(gameId: Int, mail: String): String {
        return lotgDao.getGameStatus(gameId, mail)
    }

    fun newGameAdded(stat: String, id: Int, mail: String): Long {
        return lotgDao.newGameAdded(UsersGame(id, mail, stat))
    }

    fun getAchievementStatus(achieve: Int, user: String): UsersAchievement {
        return lotgDao.getUserAchievementStatus(achieve, user)
    }


}