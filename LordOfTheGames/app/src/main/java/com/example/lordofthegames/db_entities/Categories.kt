package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ##Tag per il gioco
 *
 * @param name
 */
@Entity(tableName = "Categories")
class Categories(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag")
    var tag: String) {

}
