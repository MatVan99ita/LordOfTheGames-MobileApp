package com.example.lordofthegames.Database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lordofthegames.db_entities.*
import java.io.File
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
            Platform::class,
            User::class,
        ], version = 3)
abstract class LOTGDatabase: RoomDatabase() {

    abstract fun lotgdao(): LotgDao

    //abstract fun userDao(): UserDAO


    companion object {


        @Volatile
        private var INSTANCE: LOTGDatabase? = null

        val executor: ExecutorService = Executors.newFixedThreadPool(4)

        fun getDatabase(context: Context): LOTGDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LOTGDatabase::class.java,
                    "lotgdb"
                )
                    .createFromAsset("lotgdb.db")
                    //.addMigrations(LOTGDatabase.MIGRATION_1_2)
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}



    /**
     *  -- Inserimento del nuovo videogioco nella tabella "Videogioco"
     *  INSERT INTO Videogioco (titolo)
     *  VALUES ('Super Mario Bros.');
     *
     *  -- Recupero l'ID del nuovo videogioco
     *  SET @id_videogioco = LAST_INSERT_ID();
     *
     *  -- Inserimento delle associazioni tra il nuovo videogioco e le categorie nella tabella "VideogiocoCategoria"
     *  INSERT INTO VideogiocoCategoria (id_videogioco, id_categoria)
     *  VALUES (@id_videogioco, 1),
     *  (@id_videogioco, 2);
     */

























