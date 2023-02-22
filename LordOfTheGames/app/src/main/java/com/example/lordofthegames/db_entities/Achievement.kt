package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    )
])
class Achievement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "achievement_id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var descr: String?,

    @ColumnInfo(name = "image")
    var img: String?,

    @ColumnInfo(name = "actual_count")
    var actual_count: Int = 0,

    @ColumnInfo(name = "total_count", defaultValue = "1", typeAffinity = 3)
    var total_count: Int = 1,

    @ColumnInfo(name = "achievement_status")
    var status: Boolean = false,

    @ColumnInfo(name = "game_ref")
    var game_id: Int
    ) {
}