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

@Entity(tableName = "game")
data class Game(

    @PrimaryKey(autoGenerate = true)
    var game_id: Int,

    @ColumnInfo(defaultValue = "")
    var game_title: String,

    @ColumnInfo(defaultValue = "")
    var game_description: String,

    @ColumnInfo(defaultValue = "img")
    var game_cover: String,

    /**
     * Status -> Not played, Playing, Played, Abandoned, Wanted to play
     */
    @ColumnInfo(name = "game_status", defaultValue = "Not played")
    var game_status: String = "Not played",


    //var achievements: List<Achievement>?,
    //var categories: List<Categories>?,
    )