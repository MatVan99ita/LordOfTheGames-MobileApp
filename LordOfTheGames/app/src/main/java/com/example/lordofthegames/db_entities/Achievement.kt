package com.example.lordofthegames.db_entities

import androidx.room.*

/**
 * ##Achievement di gioco
 * @param name nome dell'achievement
 * @param descr descrizione dell'achievement(può essere vuoto)
 * @param img immagine dell'achievement(può essere vuoto)
 * @param count Conta degli elementi che servono per completare l'achievement (Default value: 1)
 * @param status Se è completato o no(Default value: false)
 */

@Entity(tableName = "achievement", foreignKeys = [
    ForeignKey(
        entity = Game::class,
        parentColumns = ["game_id"],
        childColumns = ["game_ref"],
    )
],
indices = [Index("achievement_id"), Index("game_ref")]
    )

data class Achievement(
    @PrimaryKey(autoGenerate = true)
    var achievement_id: Int,
    var name: String,
    var description: String,
    var img: String,
    var actual_count: Int = 0,
    var total_count: Int = 1,
    var status: Boolean = false,
    var game_ref: Int
    )