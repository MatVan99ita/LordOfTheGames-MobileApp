package com.example.lordofthegames.Database

import android.content.Context
import androidx.room.*
import com.example.lordofthegames.Database.LotgDao
import com.example.lordofthegames.db_entities.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [
            Achievement::class,
            Categories::class,
            Comments::class,
            Discussion::class,
            Game::class,
            GameCategory::class,
            GamePlatform::class,
            Notes::class,
            Notification::class,
            Platform::class,
            User::class,
            UsersAchievement::class,
            UsersGame::class,
        ], version = 1)
abstract class LOTGDatabase: RoomDatabase() {

    abstract fun lotgdao(): LotgDao
    companion object {
        @Volatile
        private var INSTANCE: LOTGDatabase? = null
        val executor: ExecutorService = Executors.newFixedThreadPool(4)
        fun getDatabase(context: Context): LOTGDatabase {
            return synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LOTGDatabase::class.java,
                    "lotgdb")
                    .createFromAsset("lotgdb.db")
                    .allowMainThreadQueries()
                    .build()
                instance
            }
        }
    }
}