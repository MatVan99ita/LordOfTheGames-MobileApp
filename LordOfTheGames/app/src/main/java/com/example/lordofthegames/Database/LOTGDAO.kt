package com.example.lordofthegames.Database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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


    /**
     *
     *
     * Per selezionare i giochi che fanno riferimento ad una certa categoria
     *
     * SELECT Videogioco.*
    FROM Videogioco
    JOIN VideogiocoCategoria ON VideogiocoCategoria.id_gioco = Videogioco.id
    JOIN Categoria ON Categoria.id = VideogiocoCategoria.id_categoria
    WHERE Categoria.nome = 'Avventura';

     */
    @Transaction
    @Query("SELECT * FROM game ORDER BY game_title")
    fun getGame(): LiveData<List<Game>>
    fun getAchievementListCard(): LiveData<List<Achievement?>?>?
    fun getCategory(): LiveData<List<Categories?>?>?
    fun getNote(): LiveData<List<Note?>?>?
}