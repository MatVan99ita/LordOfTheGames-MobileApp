package com.example.lordofthegames.Database

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lordofthegames.db_entities.*
import java.util.*
import kotlinx.coroutines.flow.Flow


class LOTGRepository(
    private val lotgdao: LOTGDAO,
    var application: Application
    ) {
    val allGames: LiveData<List<Game>> = lotgdao.getAllGames()

    //private lateinit var game_card: LiveData<List<Game>>
    //private lateinit var achievement_card_list: LiveData<List<Achievement>>
    //private lateinit var categories_list: LiveData<List<Categories>>
    //private lateinit var note_elem: LiveData<List<Notes>>
    //private lateinit var discussion_elem: LiveData<List<Discussion>>
    //private lateinit var comment_elem: LiveData<List<Comments>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGame(game: Game){
        lotgdao.insertGame(game)
    }


}