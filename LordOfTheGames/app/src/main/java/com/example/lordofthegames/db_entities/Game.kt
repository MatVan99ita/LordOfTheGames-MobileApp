package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * (*) -> da scegliere un nome migliore o comunque da modificare
 */

/**
 * ##La classe per identificare i giochi
 * @param name         Nome del gioco
 * @param image        link della copertina di gioco
 * @param status       Stato del gioco(wishlist/playing/dropped/finished/to add/need to be played(*))
 * @param achievements Lista degli obbiettivi
 * @param categories   Categorie del gioco
 * @param note         Il blocco note per segnarsi le cose da ricordarsi o quello che si vuole
 */

@Entity(tableName = "games")
data class Game(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var id: Int,

    @ColumnInfo(name = "game_title")
    var name: String,



    @ColumnInfo(name = "game_cover")
    var image: String?,


    @ColumnInfo(name = "game_status", defaultValue = "Not played")
    var status: String,


    var achievements: List<Achievement>?,
    var categories: List<Categories>?,
    ) {

    companion object {
    }

}