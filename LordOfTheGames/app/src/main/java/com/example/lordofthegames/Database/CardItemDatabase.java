package com.example.progetto.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.progetto.CardItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class that identifies the DB: we specify the entities inside and the DB version
 */
@Database(entities = {CardItem.class}, version = 1)
public abstract class CardItemDatabase extends RoomDatabase {

    public abstract CardItemDAO cardItemDAO();

    ///Singleton instance to retrieve when the db is needed
    private static volatile CardItemDatabase INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static CardItemDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            //The synchronized is to prevent multiple instances being created.
            synchronized (CardItemDatabase.class) {
                //If the db has not yet been created, the builder creates it.
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardItemDatabase.class, "travel_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
