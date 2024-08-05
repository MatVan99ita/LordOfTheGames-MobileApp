package com.example.lordofthegames.Database

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.Comments
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
        return lotgDao.insertUser(user.mail, user.nickname, user.password, user.photo, user.position)
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

    fun getNotes(game_ref: Int, user_ref: String): Cursor {
        return lotgDao.getNotes(game_ref, user_ref)
    }

    fun newNote(notes: Notes): Long {
        Log.i("LO NOTO", notes.toString())
        return lotgDao.insertNotes(notes.title, notes.content, notes.last_modified, notes.game_ref, notes.user_ref)
    }

    fun saveNotes(content: String, lastMod: String, gameRef: Int, user_ref: String): Int {
        return lotgDao.saveNotification(content, lastMod, gameRef, user_ref)
    }

    fun saveNotes(content: Notification): Long {
        return lotgDao.saveNotification(content.title!!, content.content!!, content.usr_ref)
    }

    fun notificationRead(user_ref: String, id: Int): Int {
        return lotgDao.notificationRead(user_ref, id)
    }

    fun getNotification(user_ref: String): Cursor {
        return lotgDao.getNotification(user_ref)
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

    fun getAchievementStatus(achieve: Int, user: String): UsersAchievement? {
        return lotgDao.getUserAchievementStatus(achieve, user)
    }

    fun getUsrImg(mail: String): String? {
        return lotgDao.getUserImg(mail)
    }

    fun updateUsrImg(img: String, mail: String): Int {
        return lotgDao.updateUsrImg(img, mail)
    }

    fun getUser(mail: String): User {
        return lotgDao.getUsr(mail)
    }

    fun sendNotification(title: String, content: String, usr_ref: String): Long {
        return lotgDao.saveNotification(title, content, usr_ref)
    }

    fun insertComment(comments: Comments): Long {
        return lotgDao.insertComment(comments)
    }

    fun saveNewDiscussion(discussion: Discussion): Long {
        return lotgDao.insertDiscussion(discussion)
    }

    fun upComment(commentId: Int): Int {
        return lotgDao.upComment(commentId)
    }

    fun downComment(commentId: Int): Int {
        return lotgDao.downComment(commentId)
    }

    fun deUpComment(commentId: Int): Int {
        return lotgDao.deUpComment(commentId)
    }

    fun deDownComment(commentId: Int): Int{
        return lotgDao.deDownComment(commentId)
    }

    fun createAURecord(achieveId: Int, userRef: String, actual: Int, status: Int): Long {
        return lotgDao.createAURecord(achieveId, userRef, actual, status)
    }

    fun uaExist(userRef: String, achieveId: Int): Boolean {
        return lotgDao.uaExist(userRef, achieveId)
    }

    fun ugExist(userRef: String, gameId: Int): Boolean {
        return lotgDao.ugExist(userRef, gameId)
    }

    fun ugCreate(user_ref: String, game_id: Int, game_status: String): Long{
        return lotgDao.ugCreate(user_ref, game_id, game_status)
    }

    fun readAllNotification(userRef: String): Int {
        return lotgDao.readAllNotification(userRef)
    }

    fun deleteNotification(id: Int, usrRef: String): Int {
        return lotgDao.deleteNotification(id, usrRef)
    }

    fun getNonReadNotificationCount(userRef: String): Int {
        return lotgDao.getNonReadNotificationCount(userRef)
    }

    fun getPlatforms(): Cursor {
        return lotgDao.getPlatforms()
    }
    fun getCategories(): Cursor {
        return lotgDao.getCategories()
    }

    fun getCompletedGameCount(userRef: String): Int {
        return lotgDao.getCompletedGameCount(userRef)
    }

    fun getCompletedAchievementCount(userRef: String): Int {
        return lotgDao.getAchievementCount(userRef)
    }

    fun getDiscussionCount(user_ref: String): Int{
        return lotgDao.getAchievementCount(user_ref)
    }

    fun getCommentCount(user_ref: String): Int{
        return lotgDao.getCommentCount(user_ref)
    }

    fun getLikeCount(user_ref: String): Int{
        return lotgDao.getLikeCount(user_ref)
    }


}