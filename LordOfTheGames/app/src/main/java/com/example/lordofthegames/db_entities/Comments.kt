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
],
indices = [Index("comment_id"), Index("discussion_ref")]
)
data class Comments(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    var id: Int,

    @ColumnInfo(name = "discussion_ref")
    var discussion_ref: Int,

    @ColumnInfo(name = "content")
    var content: String,

)