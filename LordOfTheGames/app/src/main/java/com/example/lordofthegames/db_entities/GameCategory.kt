package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "GameCategory",

    primaryKeys = ["game_ref, category_ref"],

    foreignKeys = [
    ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    ),
    ForeignKey(
        entity = Categories::class,
        childColumns = ["category_ref"],
        parentColumns = ["tag"]
    )
])
data class GameCategory(
    @ColumnInfo(name = "game_ref")
    var game_id: Int,

    @ColumnInfo(name = "category_ref")
    var cat_id: Int
) {
}