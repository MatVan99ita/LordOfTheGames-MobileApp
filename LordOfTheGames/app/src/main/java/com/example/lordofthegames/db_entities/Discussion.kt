package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "game_discussion", foreignKeys = [
    ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    )
])
class Discussion(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "discussion_id")
    var id: Int,

    @ColumnInfo(name = "discussion_object")
    var title: String,

    @ColumnInfo(name = "discussion_body")
    var content: Any?,

) {
}