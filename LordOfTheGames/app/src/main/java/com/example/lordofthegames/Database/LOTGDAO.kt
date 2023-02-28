package com.example.lordofthegames.Database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lordofthegames.db_entities.*
import com.example.lordofthegames.recyclerView.GameCardItem
import java.util.*

@Dao
interface LOTGDAO {

    /** //// ACHIEVEMENT /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAchievement(achievement: Achievement)
    @Update
    fun updateAchievement(id: Int, achievement: Achievement)

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// CATEGORIES //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: GameCategory)

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// COMMENTS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Insert
    fun insertComment(comments: Comments)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComment(id: Int, value: Any?)

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// DISCUSSION //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDiscussion(id: Int, value: Any?)

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// GAME CATEGORY ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */



    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// GAME ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(game: Game)

    @Transaction
    @Query("SELECT * FROM game ORDER BY game_title")
    fun getGame(): LiveData<List<Game>>

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    /** //// NOTES ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(notes: Notes)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteDate(id: Int, data: Date)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteContent(id: Int, value: Any?)

    /** //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */
















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



    /**
     * scrivendo `@Update(onConflict = OnConflictStrategy.REPLACE) fun myFun(data: MyData)` mi dà errore "Type of the paramenter must be a class annotated with @Entity or a collection/array of it" e non so come correggere questo errore
     */








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