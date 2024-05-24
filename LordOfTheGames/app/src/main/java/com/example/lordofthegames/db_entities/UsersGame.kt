package com.example.lordofthegames.db_entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import org.jetbrains.annotations.NotNull


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

/*
TableInfo{
    name='UsersGame', columns={

        game_status =Column{name='game_status', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='undefined'},
        game_status1=Column{name='game_status', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'}},

        user_ref =Column{name='user_ref', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=2, defaultValue='undefined'},
        user_ref1=Column{name='user_ref', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=2, defaultValue='undefined'},

        game_ref =Column{name='game_ref', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='undefined'}}
        game_ref1=Column{name='game_ref', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=1, defaultValue='undefined'}

    foreignKeys=[
        ForeignKey{referenceTable='game', onDelete='NO ACTION +', onUpdate='NO ACTION', columnNames=[game_ref], referenceColumnNames=[game_id]},
        ForeignKey{referenceTable='user', onDelete='NO ACTION +', onUpdate='NO ACTION', columnNames=[user_ref], referenceColumnNames=[mail]}],
    indices=[
        Index{name='index_UsersGame_game_ref', unique=false, columns=[game_ref], orders=[ASC]'}, Index{name='index_UsersGame_user_ref', unique=false, columns=[user_ref], orders=[ASC]'}]}

Found:
TableInfo{
    name='UsersGame', columns={

    foreignKeys=[ForeignKey{referenceTable='user', onDelete='NO ACTION +', onUpdate='NO ACTION', columnNames=[user_ref], referenceColumnNames=[mail]}, ForeignKey{referenceTable='game', onDelete='NO ACTION +', onUpdate='NO ACTION', columnNames=[game_ref], referenceColumnNames=[game_id]}], indices=[Index{name='index_UsersGame_user_ref', unique=false, columns=[user_ref], orders=[ASC]'}, Index{name='index_UsersGame_game_ref', unique=false, columns=[game_ref], orders=[ASC]'}]}

*/