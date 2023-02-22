package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "comment", foreignKeys = [
    ForeignKey(
        entity = Discussion::class,
        childColumns = ["discussion_ref"],
        parentColumns = ["discussion_id"]
    )
])
class Comments(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    var id: Int,

    @ColumnInfo(name = "discussion_ref")
    var discussion_ref: Int,

    @ColumnInfo(name = "content")
    var content: Any,

) {
}