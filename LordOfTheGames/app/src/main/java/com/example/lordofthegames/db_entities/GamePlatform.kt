package com.example.lordofthegames.db_entities

import androidx.room.*

@Entity(tableName = "gameplatform",

    primaryKeys = ["game_ref", "platform_ref"],
    foreignKeys = [
    ForeignKey(
        entity = Game::class,
        childColumns = ["game_ref"],
        parentColumns = ["game_id"]
    ),
    ForeignKey(
        entity = Platform::class,
        childColumns = ["platform_ref"],
        parentColumns = ["platform_id"]
    )],
    indices = [Index("game_ref"), Index("platform_ref")],
)
data class GamePlatform(
    var game_ref: Int,
    var platform_ref: Int
)