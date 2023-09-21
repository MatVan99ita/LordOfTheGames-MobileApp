package com.example.lordofthegames.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.User


class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val mText: MutableLiveData<String> = MutableLiveData<String>()
    private val repository: LotgRepo = LotgRepo(application)

    fun getText(): LiveData<String?> {
        return mText
    }

    fun getCurrentUser(user_name: String, passw: String): LiveData<List<User?>?> {
        return repository.getCurrentUser(user_name, passw)
    }

}