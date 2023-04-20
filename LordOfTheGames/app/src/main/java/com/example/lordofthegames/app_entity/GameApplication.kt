package com.example.lordofthegames.app_entity

import android.app.Application
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.Database.LOTGRepository

class GameApplication: Application() {
    // lazy --> the database and the repository are only created when they're needed
    private val database by lazy { LOTGDatabase.getDatabase(this) }
    val repository by lazy { LOTGRepository(database.lotgdao()) }

}