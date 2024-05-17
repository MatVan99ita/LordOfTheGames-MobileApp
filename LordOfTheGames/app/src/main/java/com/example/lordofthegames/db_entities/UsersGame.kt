package com.example.lordofthegames.db_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(tableName = "UsersGame",

    primaryKeys = ["game_ref", "user_ref"],
    foreignKeys = [
        ForeignKey(
            entity = Game::class,
            childColumns = ["game_ref"],
            parentColumns = ["game_id"]
        ),
        ForeignKey(
            entity = User::class,
            childColumns = ["user_ref"],
            parentColumns = ["mail"]
        )],
    indices = [Index("game_ref"), Index("user_ref")],
)
data class UsersGame(
    var game_ref: Int,
    var user_ref: String,
    /**
     * Status -> Playing, Played, Abandoned, Wanted to play
     */
    var game_status: String,
)
