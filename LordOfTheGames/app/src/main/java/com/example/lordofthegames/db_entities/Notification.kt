package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notification",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["nickname"],
        childColumns = ["usr_ref"]
    )],
)
data class Notification(
    @PrimaryKey(autoGenerate = true, )
    val id: Int,
    val title: String?,
    val content: String?,
    val data_inizio: String?,
    val data_fine: String?,
    val read: Int = 0,
    val usr_ref: String,
)

