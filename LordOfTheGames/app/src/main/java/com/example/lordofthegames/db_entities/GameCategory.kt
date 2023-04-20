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
        parentColumns = ["category_id"]
    )],
    indices = [Index("game_ref"), Index("category_ref")],
)
data class GameCategory(
    var game_ref: Int,
    var category_ref: Int
)