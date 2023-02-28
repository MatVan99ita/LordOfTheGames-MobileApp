package com.example.lordofthegames.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lordofthegames.db_entities.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities =
                [
                    Game::class,
                    Achievement::class,
                    Categories::class,
                    Notes::class,
                    GameCategory::class,
                    Discussion::class,
                    Comments::class
                ], version = 1, exportSchema = true)
abstract class LOTGDatabase: RoomDatabase() {

    abstract fun lotgDAO(): LOTGDAO?

    companion object {
        private var INSTANCE: LOTGDatabase? = null
        val executor: ExecutorService = Executors.newFixedThreadPool(4)
        fun getDatabase(context: Context): LOTGDatabase?{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LOTGDatabase::class.java, "lotg_db"
                ).build()
            }
            return INSTANCE
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

}