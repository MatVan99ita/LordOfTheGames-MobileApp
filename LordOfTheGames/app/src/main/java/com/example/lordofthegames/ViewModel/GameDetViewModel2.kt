package com.example.lordofthegames.ViewModel

import android.app.Application
import android.graphics.Color
import android.util.Log
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.UsersAchievement
import com.example.lordofthegames.recyclerView.AchievementCardItem
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

    fun getGameAchievement(game_title: String, user_ref: String): List<AchievementCardItem> {
        val a = repository.getGameAchievement(game_title)
        val l: MutableList<AchievementCardItem> = mutableListOf()
        while (a.moveToNext()) {
            val ua: UsersAchievement? = repository.getAchievementStatus(
                a.getInt(a.getColumnIndexOrThrow("achievement_id")),
                user_ref,
            )
            l.add(
                AchievementCardItem(
                    achieve_id = a.getInt(a.getColumnIndexOrThrow("achievement_id")),
                    img = a.getString(a.getColumnIndexOrThrow("img")),
                    name = a.getString(a.getColumnIndexOrThrow("name")),
                    descr = a.getString(a.getColumnIndexOrThrow("description")),
                    actual_count = ua?.actual_count ?: 0,
                    total_count = a.getInt(a.getColumnIndexOrThrow("total_count")),
                    completed = ua?.status ?: 0,
                )
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

    fun updateGameStatus(game_status: String, game_id: Int, user_ref: String): Int {
        return repository.updateGameStatus(game_status, game_id, user_ref)
    }

    fun updateAchievement(achieve_id: Int, actual: Int, user_ref: String): Int {
        return repository.updateAchievement(achieve_id, actual, user_ref)
    }
    fun completeAchievement(achieve_id: Int, user_ref: String): Int {
        return repository.completeAchievement(achieve_id, user_ref)
    }


}