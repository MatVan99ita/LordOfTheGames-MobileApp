package com.example.lordofthegames.home.mygame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.recyclerView.DiscussionItem
import com.example.lordofthegames.recyclerView.MyGameListItem

class MyGameListViewModel(application: Application): AndroidViewModel(application) {

    private val repository: LotgRepo = LotgRepo(application)

    private val _index = MutableLiveData<Int>()



    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getOrderedFilt(user_ref: String): List<MyGameListItem>{
        val c = repository.getOrderedFilt(user_ref)
        val l = arrayListOf<MyGameListItem>()
        while (c.moveToNext()){
            l.add(
                MyGameListItem( //game_id, game_title, game_status
                    game_id = c.getInt(c.getColumnIndexOrThrow("game_id")),
                    game_title = c.getString(c.getColumnIndexOrThrow("game_title")),
                    game_cover = c.getString(c.getColumnIndexOrThrow("game_cover")),
                    game_status = c.getString(c.getColumnIndexOrThrow("game_status"))
                )
            )
        }
        return l
    }
}