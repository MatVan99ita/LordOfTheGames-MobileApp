package com.example.lordofthegames.Database

import androidx.lifecycle.LiveData
import com.example.lordofthegames.games.Achievement
import com.example.lordofthegames.games.Categories
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.games.Notes

class LOTGRepository(
    val LOTGDAO: LOTGDAO,
    val game_card: LiveData<List<Game>>,
    val achievement_card_list: LiveData<List<Achievement>>,
    val categories_list: LiveData<List<Categories>>,
    val note_elem: LiveData<List<Notes>>
    ) {

}