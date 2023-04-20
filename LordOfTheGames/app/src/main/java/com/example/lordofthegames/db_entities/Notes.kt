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
        parentColumns = ["game_id"],
        childColumns = ["game_ref"],
    )],
)
@TypeConverters(DateConverter::class)
data class Notes(
    /**
     * ##Le note dell'utente per un gioco
     * @param title
     * @param content
     */
    @PrimaryKey(autoGenerate = true)
    var note_id: Int,

    @ColumnInfo(defaultValue = "")
    var title: String,

    @ColumnInfo(defaultValue = "")
    var content: String,

    @ColumnInfo(defaultValue = "")
    var last_modified: String,

    var game_ref: Int )