package com.example.lordofthegames.db_entities

import androidx.room.*
import com.example.lordofthegames.Database.AnyTypeConverter
import java.util.Date

@Entity(tableName = "comment", foreignKeys = [
    ForeignKey(
        entity = Discussion::class,
        childColumns = ["discussion_ref"],
        parentColumns = ["discussion_id"]
    )
]
)
data class Comments(

    @PrimaryKey(autoGenerate = true)
    var comment_id: Int,
    var discussion_ref: Int,
    var content: String,

)