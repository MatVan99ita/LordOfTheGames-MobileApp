package com.example.lordofthegames.Database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lordofthegames.db_entities.*
import com.example.lordofthegames.recyclerView.GameCardItem
import java.util.*

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
    //fun insertComplex()


    /**
     *
     *
     * Per selezionare i giochi che fanno riferimento ad una certa categoria
     *
     * SELECT Videogioco.*
     * FROM Videogioco
     * JOIN VideogiocoCategoria ON VideogiocoCategoria.id_gioco = Videogioco.id
     * JOIN Categoria ON Categoria.id = VideogiocoCategoria.id_categoria
     * WHERE Categoria.nome = 'Avventura';
     *
     */
    @Transaction
    @Query("SELECT * FROM game ORDER BY game_title")
    fun getGame(): LiveData<List<Game>>


    /**
     * scrivendo `@Update(onConflict = OnConflictStrategy.REPLACE) fun myFun(data: MyData)` mi dà errore "Type of the paramenter must be a class annotated with @Entity or a collection/array of it" e non so come correggere questo errore
     */


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteDate(data: Date)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDiscussion(value: String)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteContent(value: String)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComment(value: String)



    //fun getAchievementListCard(): LiveData<List<Achievement?>?>?
    //fun getCategory(): LiveData<List<Categories?>?>?
    //fun getNote(): LiveData<List<Note?>?>?
/*
    @Query("UPDATE notes SET last_modified = :currentDateTime WHERE note_id = :id")
    fun updateNotesTimestamp(id: Int, currentDateTime: Long)

    @Insert
    fun insertDate(data: Long)

    @Query("UPDATE notes SET note_content = :value WHERE note_id = :id")
    fun updateNotes(id: Int, value: String)

    @Insert
    fun insertNoteContent(value: String)

    @Query("UPDATE comment SET content = :value WHERE comment_id = :id")
    fun updateCommentContent(id: Int, value: String)

    @Insert
    fun insertCommentContent(value: String)

    @Query("UPDATE game_discussion SET discussion_body = :value WHERE discussion_id = :id")
    fun updateDiscussionContent(id: Int, value: String)

    @Insert
    fun insertDiscussion(value: String)
*/

}