package com.example.lordofthegames.Database
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lordofthegames.db_entities.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.stream.Collectors
import kotlin.reflect.KClass

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
        ], version = 3, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class LOTGDatabase: RoomDatabase() {

    abstract fun lotgdao(): LOTGDAO

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
                ).build()
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

























