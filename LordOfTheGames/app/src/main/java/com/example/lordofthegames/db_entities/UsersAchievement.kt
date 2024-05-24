package com.example.lordofthegames.db_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import org.jetbrains.annotations.NotNull


@Entity(tableName = "UsersAchievement",

    primaryKeys = ["user_ref", "achieve_id"],
    foreignKeys = [
        ForeignKey(
            entity = Achievement::class,
            childColumns = ["achieve_id"],
            parentColumns = ["achievement_id"]
        ),
        ForeignKey(
            entity = User::class,
            childColumns = ["user_ref"],
            parentColumns = ["mail"]
        )],
    indices = [Index("user_ref"), Index("achieve_id")],
)
data class UsersAchievement(
    var user_ref: String,
    var achieve_id: Int,
    /**
     * Status -> Playing, Played, Abandoned, Wanted to play
     */
    var actual_count: Int = 0,
    var status: String = "NC",
)
