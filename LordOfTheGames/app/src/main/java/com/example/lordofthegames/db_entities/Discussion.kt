package com.example.lordofthegames.db_entities

import androidx.room.*
import com.example.lordofthegames.Database.AnyTypeConverter
import java.util.Date

@Entity(tableName = "game_discussion", foreignKeys = [
    ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    )],
    indices = [Index("discussion_id"), Index("game_ref")]
)
data class Discussion(

    @PrimaryKey(autoGenerate = true)
    var discussion_id: Int,
    var title: String,
    var content: String,
    var game_ref: Int

)