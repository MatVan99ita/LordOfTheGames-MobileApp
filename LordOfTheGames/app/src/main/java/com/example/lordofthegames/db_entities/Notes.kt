package com.example.lordofthegames.db_entities

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    )]
)
data class Notes(
    /**
     * ##Le note dell'utente per un gioco
     * @param title
     * @param content
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id: Int,

    @ColumnInfo(name = "note_title")
    var title: String,

    @ColumnInfo(name = "note_content")
    var content: String,

    @ColumnInfo(name = "last_modified")
    var lastModified: DateTimeFormatter,

    @ColumnInfo(name = "game_ref")
    var gameId: Int ) {
}