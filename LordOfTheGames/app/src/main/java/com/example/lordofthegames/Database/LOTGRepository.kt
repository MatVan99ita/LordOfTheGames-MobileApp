package com.example.lordofthegames.Database

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.*


class LOTGRepository(
    var application: Application
    ) {

    lateinit var LOTGDAO: LOTGDAO
    lateinit var game_card: LiveData<List<Game>>
    lateinit var achievement_card_list: LiveData<List<Achievement>>
    lateinit var categories_list: LiveData<List<Categories>>
    lateinit var note_elem: LiveData<List<Notes>>
    lateinit var discussion_elem: LiveData<List<Discussion>>
    lateinit var comment_elem: LiveData<List<Comments>>

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


}