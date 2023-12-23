package com.example.lordofthegames.user_login

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.User

class LoggedViewModel(application: Application): AbstractViewModel(application) {

    fun insertNewUsr(user: User): Long{
        return repository.insertUser(user)
    }

    fun getUsrByMailPass(mail: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(mail, passw)
    }

    fun getUsrByNick(nick: String): LiveData<List<User?>?> {
        return repository.getUserByN(nick)
    }

    fun getAllGames(): LiveData<List<Game?>?>{
        return repository.getAllGames()
    }
}