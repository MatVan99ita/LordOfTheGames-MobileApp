package com.example.lordofthegames.EntityApp

import android.app.Application
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.EntityRepo.UserRepo

class UserApplication: Application()  {
    private val database by lazy { LOTGDatabase.getDatabase(this) }
    val repository by lazy { UserRepo(database.userDao()) }
}