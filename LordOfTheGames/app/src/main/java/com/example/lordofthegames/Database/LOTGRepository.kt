package com.example.lordofthegames.Database

import androidx.annotation.WorkerThread
import com.example.lordofthegames.db_entities.*
import kotlinx.coroutines.flow.Flow


class LOTGRepository(
    private val lotgdao: LOTGDAO
    ) {
    val allGames: Flow<List<Game>> = lotgdao.getGames()

    //private lateinit var game_card: LiveData<List<Game>>
    //private lateinit var achievement_card_list: LiveData<List<Achievement>>
    //private lateinit var categories_list: LiveData<List<Categories>>
    //private lateinit var note_elem: LiveData<List<Notes>>
    //private lateinit var discussion_elem: LiveData<List<Discussion>>
    //private lateinit var comment_elem: LiveData<List<Comments>>
/*
    @Suppress("RedundantSuspendModifier")
*/
    @WorkerThread
    fun insertGame(game: Game){
        lotgdao.insertGame(game)
    }

    @WorkerThread
    fun insertUser(user: User){
        lotgdao.insertUser(user)
    }

    fun getAllGame(): Flow<List<Game>> {
        return allGames
    }

    fun getUser(mail: String): Array<String> {
        return lotgdao.getUser(mail)
    }


}