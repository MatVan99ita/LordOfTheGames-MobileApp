package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * ##Tag per il gioco
 *
 * @param name
 */
@Entity(tableName = "Categories", indices = [Index("tag")])
data class Categories(

    @PrimaryKey()
    @ColumnInfo(name = "tag")
    var tag: String,

    @ColumnInfo(name = "full_name")
    var nome: String
    )