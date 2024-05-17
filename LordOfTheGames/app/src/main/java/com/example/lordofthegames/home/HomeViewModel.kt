package com.example.lordofthegames.home

import android.app.Application
import android.database.Cursor
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.Pair
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.UsersGame
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.PlatformCardItem


class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val mText: MutableLiveData<String> = MutableLiveData<String>()
    private val repository: LotgRepo = LotgRepo(application)

    fun modifyGameStatus(stat: String, id: Int, mail: String){
        repository.modifyGameStatus(stat, id, mail)
    }

    fun getCurrentUser(user_name: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(user_name, passw)
    }

    fun getAllGameSimpleDet(game_title: String = "", order: String = "Name"): Cursor {
        return when(order){
            "Name" -> repository.getAllGameSimpleDet("%${game_title}%")
            "Platform" -> repository.getAllGameSimpleDetP("%${game_title}%")
            "Categories" -> repository.getAllGameSimpleDetC("%${game_title}%")
            else -> repository.getAllGameSimpleDet("%${game_title}%")
        }
    }

    fun getGameSimpleDet(): LiveData<List<String>> {
        return repository.getGameSimpleDet()
    }



    fun getGameAchievementCount(game_title: String): Cursor {
        return repository.getAchievementCount(game_title)
    }

    /**
     * Platform W  platform_id
     * Platform W  nome
     * Platform W  icona
     * */
    fun getGamePlatform(game_title: String): MutableList<PlatformCardItem> {
        val l = repository.getGamePlatform(game_title)
        val p : MutableList<PlatformCardItem> = mutableListOf()

        while (l.moveToNext()){
            val el  = l.getString(1);
            p.add(
                PlatformCardItem(
                    el,
                    when(el){
                        "Steam" -> Color.rgb(41, 41, 41)
                        "Epic" -> Color.rgb(58, 58, 56)
                        "Xbox One" -> Color.rgb(24, 128, 24)
                        "Game Pass" -> Color.rgb(24, 128, 24)
                        "Nintendo switch" -> Color.rgb(231, 8, 25)
                        "Playstation 4" -> Color.rgb(19, 44, 116)
                        "Playstation 5" -> Color.rgb(19, 44, 116)
                        else -> R.color.green_light_variant
                    }
                )
            )
        }

        return p
    }

    /**
     * Categories   W  category_id
     * Categories   W  category_name
     * */
    fun getGameCategory(game_title: String): MutableList<CategoryCardItem> {
        val c : MutableList<CategoryCardItem> = mutableListOf()

        val l = repository.getGameCategory(game_title)

        while (l.moveToNext()){
            c.add(CategoryCardItem(l.getString(1)))
        }

        return c
    }

    fun getAllGame(): LiveData<List<Game?>?> {
        return repository.getAllGames()
    }

    /**
     * Count W  total_count
     * Count W  completed_count
     * */
    fun getAchievementCount(game_title: String): Pair<Int, Int> {
        val l = repository.getAchievementCount(game_title)
        l.moveToFirst()
        return Pair(l.getInt(1), l.getInt(0));
    }

    fun getPrintableAchivement(game_title: String): String {
        val l = repository.getAchievementCount(game_title)
        return "${l.getInt(0)}/${l.getInt(1)}"
    }

    fun getSIMP(): List<GameCardItem> {
        val l = repository.getSimp()
        Log.w("POCODIO1", l.columnCount.toString())
        Log.w("POCODIO2", "${l.getColumnName(0)} - ${l.getColumnName(1)} - ${l.getColumnName(2)}")
        Log.w("POCODIO3", l.count.toString())
        val s = mutableListOf<GameCardItem>()


        while (l.moveToNext()){
            s.add(GameCardItem(l.getString(0).toString(), l.getString(1).toString(), l.getInt(2)))
        }
        s.forEach{el -> Log.w("POCODIO4", el.toString())}

        return s
    }

    fun getGameListValidity(game_title: String, user_ref: String): Boolean {
        return repository.getGameListValidity(game_title, user_ref) >= 0
    }

    fun isGameAdded(usersGame: UsersGame): Boolean{
        return repository.insertUsersGame(usersGame) > 0
    }

}