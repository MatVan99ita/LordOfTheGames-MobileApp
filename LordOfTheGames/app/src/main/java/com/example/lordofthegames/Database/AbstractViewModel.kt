package com.example.lordofthegames.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LotgRepo

abstract class AbstractViewModel(application: Application): AndroidViewModel(application) {

    private val mText: MutableLiveData<String> = MutableLiveData<String>()
    val repository: LotgRepo = LotgRepo(application)

    fun getText(): LiveData<String?> {
        return mText
    }
}