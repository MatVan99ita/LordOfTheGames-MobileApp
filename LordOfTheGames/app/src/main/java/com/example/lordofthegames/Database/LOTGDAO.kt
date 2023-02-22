package com.example.lordofthegames.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.lordofthegames.games.Achievement
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.games.GameCategory
import com.example.lordofthegames.games.Notes
import com.example.lordofthegames.recyclerView.CardItem

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

    fun getGame(): LiveData<List<CardItem?>?>?
    fun getAchievement(): LiveData<List<CardItem?>?>?
    fun getCategory(): LiveData<List<CardItem?>?>?
    fun getNote(): LiveData<List<CardItem?>?>?
}