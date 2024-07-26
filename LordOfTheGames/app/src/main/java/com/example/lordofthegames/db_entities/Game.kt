package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lordofthegames.Enum.PlatformEnum

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
    var game_title: String,
    var game_description: String,
    var game_cover: String="img",
    )

//TODO: aggiungere il link la copertina
//      per il carosello e tutte le info aggiuntive mettere un TBD per evitare rotture di coglioni