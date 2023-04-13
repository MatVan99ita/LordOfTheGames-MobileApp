package com.example.lordofthegames.db_entities

import androidx.room.*

@Entity(tableName = "GameCategory",

    primaryKeys = ["game_ref", "category_ref"],
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
    )],
    indices = [Index("game_ref"), Index("category_ref")],
)
data class GameCategory(
    @ColumnInfo(name = "game_ref")
    var game_ref: Int,

    @ColumnInfo(name = "category_ref")
    var cat_ref: Int
)