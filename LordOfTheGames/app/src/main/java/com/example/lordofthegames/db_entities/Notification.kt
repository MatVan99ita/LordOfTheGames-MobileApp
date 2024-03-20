package com.example.lordofthegames.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notification"
)
data class Notification(
    @PrimaryKey(autoGenerate = true, )
    val id: Int,
    val title: String?,
    val content: String?,
    val data_inizio: String?,
    val data_fine: String?,
    @ColumnInfo(defaultValue = "0")
    val read: Int,
)

/*

 Expected:
TableInfo{name='notification', columns={


E user_ref=Column{name='user_ref', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='undefined'},
F user_ref=Column{name='user_ref', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'}},






TableInfo{name='notification', columns={




*/
