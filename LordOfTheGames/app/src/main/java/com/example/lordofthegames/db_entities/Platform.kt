package com.example.lordofthegames.db_entities

import androidx.room.*

@Entity(tableName = "Platform")
data class Platform(
    @PrimaryKey(autoGenerate = true)
    var platform_id: Int,
    @ColumnInfo(defaultValue = "")
    var nome: String,
    @ColumnInfo(defaultValue = "img")
    var icona: String,
)