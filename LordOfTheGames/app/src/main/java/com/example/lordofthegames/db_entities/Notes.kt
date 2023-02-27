package com.example.lordofthegames.db_entities

import androidx.room.*
import com.example.lordofthegames.Database.AnyTypeConverter
import com.example.lordofthegames.Database.DateConverter
import java.time.format.DateTimeFormatter
import java.util.*
import java.sql.Timestamp

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    )]
)
@TypeConverters(AnyTypeConverter::class, DateConverter::class)
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
    var content: Any?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date,

    @ColumnInfo(name = "game_ref")
    var gameId: Int ) {


}