package com.example.lordofthegames.db_entities

import androidx.room.*
import com.example.lordofthegames.Database.AnyTypeConverter
import java.util.Date

@Entity(tableName = "discussion", foreignKeys = [
    ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    ),
    ForeignKey(
        entity = User::class,
        childColumns = ["user_ref"],
        parentColumns = ["nickname"]

    )

]
)
data class Discussion(

    @PrimaryKey(autoGenerate = true)
    var discussion_id: Int,
    var title: String,
    var content: String,
    @ColumnInfo(index = true)
    var game_ref: Int,
    var user_ref: String

)