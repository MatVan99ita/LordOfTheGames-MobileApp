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
    ), ForeignKey(
        entity = User::class,
        parentColumns = ["mail"],
        childColumns = ["user_ref"]
    )],

)
data class Notes(
    /**
     * ##Le note dell'utente per un gioco
     * @param title
     * @param content
     */
    @PrimaryKey(autoGenerate = true)
    var note_id: Int,
    var title: String,
    var content: String,
    var last_modified: String,
    @ColumnInfo(index = true)
    var game_ref: Int,
    var user_ref: String
    )
