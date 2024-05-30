package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user",
    indices = [Index(value = arrayOf("nickname"), unique = true)]
)
data class User(
    @PrimaryKey
    var mail: String,
    var nickname: String,
    var password: String,
    var photo: String?,
)


