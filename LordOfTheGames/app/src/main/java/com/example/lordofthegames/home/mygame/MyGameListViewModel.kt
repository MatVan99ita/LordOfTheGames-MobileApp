package com.example.lordofthegames.home.mygame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Game

class MyGameListViewModel(application: Application): AndroidViewModel(application) {

    private val repository: LotgRepo = LotgRepo(application)

    private val _index = MutableLiveData<Int>()
   //val text: LiveData<String> = Transformations.map(_index) {
   //    "Hello world from section: $it"
   //}

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getFilt(): List<Game>{
        return repository.getFilt()
    }
}