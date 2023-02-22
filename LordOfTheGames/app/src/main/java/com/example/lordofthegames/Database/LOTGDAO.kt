package com.example.lordofthegames.Database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.lordofthegames.db_entities.*
import com.example.lordofthegames.recyclerView.GameCardItem

@Dao
interface LOTGDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAchievement(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: GameCategory)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(notes: Notes)

    /**
     * funzione per l'inserimento complesso di più record per più tabelle
     */
    fun insertComplex()

    fun getGame(): LiveData<List<Game?>?>?
    fun getGameCard(): LiveData<List<GameCardItem?>?>?
    fun getAchievementListCard(): LiveData<List<Achievement?>?>?
    fun getCategory(): LiveData<List<Categories?>?>?
    fun getNote(): LiveData<List<Note?>?>?
}