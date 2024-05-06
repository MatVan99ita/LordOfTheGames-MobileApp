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

    fun getAchievementCount(game_title: String): Cursor{
        return lotgDao.getAchievementCount(game_title)
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

    fun modifyGameStatus(stat: String, id: Int){
        lotgDao.modifyGameStatus(stat, id)
    }

    fun getFilt(): List<Game> {
        return lotgDao.getAllFilteredGame()
    }

    fun updateGameStatus(game_title: String, game_status: String): Int {
        return lotgDao.updateGameStatus(game_title, game_status)
    }

    fun updateAchievement(game_title: String, id: Int, actual: Int): Int {
        return lotgDao.updateAchievement(game_title, id, actual)
    }
    fun completeAchievement(game_title: String, id: Int, status: Int): Int {
        return lotgDao.completeAchievement(game_title, id, status)
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
    fun getUserStatisticsCounts(): Cursor{
        return lotgDao.getUserStatisticsCounts()
    }

}