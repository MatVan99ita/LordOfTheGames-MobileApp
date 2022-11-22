package com.example.lordofthegames.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lordofthegames.recyclerView.CardItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [CardItem::class], version = 1)
abstract class CardItemDatabase : RoomDatabase() {
    abstract fun cardItemDAO(): CardItemDAO?

    companion object {
        @Volatile
        private var INSTANCE: CardItemDatabase? = null
        val executor: ExecutorService = Executors.newFixedThreadPool(4)
        fun getDatabase(context: Context): CardItemDatabase? {
            if (INSTANCE == null) {
                synchronized(CardItemDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CardItemDatabase::class.java, "travel_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
