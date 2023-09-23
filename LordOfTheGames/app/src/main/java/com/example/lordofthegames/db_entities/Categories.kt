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
@Entity(tableName = "categories")
data class Categories(
    @PrimaryKey()
    var category_id: Int,
    var category_name: String
    )