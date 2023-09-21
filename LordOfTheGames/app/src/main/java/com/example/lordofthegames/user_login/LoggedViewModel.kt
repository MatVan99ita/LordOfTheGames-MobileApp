package com.example.lordofthegames.user_login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.User

class LoggedViewModel(application: Application): AbstractViewModel(application) {

    fun insertNewUsr(user: User){
        repository.insertUser(user)
    }

    fun getUsr(mail: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(mail, passw)
    }
}