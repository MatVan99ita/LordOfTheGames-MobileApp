package com.example.lordofthegames.ViewModel

import android.app.Application
import android.database.Cursor
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Achievement
import com.example.lordofthegames.db_entities.Categories
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Platform
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.PlatformCardItem

class GameDetViewModel2(application: Application): AbstractViewModel(application) {


    fun getGameDetails(game_name: String): Game {
        return repository.getGameDetail(game_name)
    }

    fun getGamePlatform(game_title: String): List<PlatformCardItem> {
        val c = repository.getGamePlatform(game_title)
        val l = mutableListOf<PlatformCardItem>()

        while (c.moveToNext()){
            val el  = c.getString(1)
            Log.i("GIOOG", el)
            l.add(
                PlatformCardItem(
                    el,
                    when(el){
                        "Steam"             -> Color.argb(255, 41, 41, 41)
                        "Epic"              -> Color.argb(255, 58, 58, 56)
                        "Xbox One"          -> Color.argb(255, 24, 128, 24)
                        "Game Pass"         -> Color.argb(255, 24, 128, 24)
                        "Nintendo switch"   -> Color.argb(255, 231, 8, 25)
                        "Playstation 4"     -> Color.argb(255, 19, 44, 116)
                        "Playstation 5"     -> Color.argb(255, 19, 44, 116)
                        else -> R.color.green_light_variant
                    }
                )
            )
        }

        return l
    }

    fun getGameAchievement(game_title: String): List<Achievement> {
        val a = repository.getGameAchievement(game_title)
        val l: MutableList<Achievement> = mutableListOf()
        while (a.moveToNext()) {
            l.add(
                Achievement(a.getInt(0), a.getString(1), a.getString(2), a.getString(3), a.getInt(4), a.getInt(5), a.getInt(6), a.getInt(7))
            )
        }
        return l
    }

    fun getGameCategory(game_title: String): List<CategoryCardItem> {
        val c = repository.getGameCategory(game_title)
        val l = mutableListOf<CategoryCardItem>()
        while(c.moveToNext()){
            l.add(
                CategoryCardItem(
                    c.getString(1)
                )
            )
        }

        return l
    }

    fun updateGameStatus(game_title: String, game_status: String): Int {
        return repository.updateGameStatus(game_title, game_status)
    }

    fun updateAchievement(game_title: String, id: Int, actual: Int): Int {
        return repository.updateAchievement(game_title, id, actual)
    }
    fun completeAchievement(game_title: String, id: Int, status: Int): Int {
        return repository.completeAchievement(game_title, id, status)
    }


}