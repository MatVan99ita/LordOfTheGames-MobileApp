package com.example.lordofthegames.Database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.*
import java.util.*


class LOTGRepository(
    var application: Application
    ) {

    private lateinit var LOTGDAO: LOTGDAO
    private lateinit var game_card: LiveData<List<Game>>
    private lateinit var achievement_card_list: LiveData<List<Achievement>>
    private lateinit var categories_list: LiveData<List<Categories>>
    private lateinit var note_elem: LiveData<List<Notes>>
    private lateinit var discussion_elem: LiveData<List<Discussion>>
    private lateinit var comment_elem: LiveData<List<Comments>>

    init {

        val db: LOTGDatabase? = LOTGDatabase.getDatabase(application)
        if (db != null) {
            LOTGDAO = db.lotgDAO()!!
            game_card = LOTGDAO.getGame()
        }
    }

    fun getGame(): LiveData<List<Game>>{
        return game_card
    }

    fun updateNoteData() {
        LOTGDAO.updateNoteDate(Date())
    }

    fun updateNoteContent(value: String) {
        LOTGDAO.updateNoteContent(value)
    }

    fun updateComment(value: String) {
       LOTGDAO.updateComment(value)
    }

    fun updateDiscussion(value: String) {
        LOTGDAO.updateDiscussion(value)
    }


}