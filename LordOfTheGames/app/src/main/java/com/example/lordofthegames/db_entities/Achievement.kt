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
    @ColumnInfo(name = "achievement_id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var descr: String,

    @ColumnInfo(name = "image")
    var img: String,

    @ColumnInfo(name = "actual_count", defaultValue = "0", typeAffinity = ColumnInfo.INTEGER)
    var actual_count: Int = 0,

    @ColumnInfo(name = "total_count", defaultValue = "1", typeAffinity = ColumnInfo.INTEGER)
    var total_count: Int = 1,

    @ColumnInfo(name = "achievement_status", defaultValue = "0", typeAffinity = ColumnInfo.INTEGER)
    var status: Boolean = false,

    @ColumnInfo(name = "game_ref")
    var game_id: Int
    )