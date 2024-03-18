package com.example.lordofthegames.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Notification
import com.j256.ormlite.stmt.query.Not

class NotificationViewModel(application: Application): AndroidViewModel(application) {

    private val repository: LotgRepo = LotgRepo(application)


    fun saveNotification(notes: Notification): Long{
        return repository.saveNotes(notes)
    }

    @SuppressLint("Range")
    fun getNotification(): List<Notification>{
        val c = repository.getNotification()
        val l: MutableList<Notification>

        while(c.moveToNext()){

            l.add(
                Notification(
                    c.getInt(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("title")),
                    c.getString(c.getColumnIndex("content")),
                    c.getString(c.getColumnIndex("data_inizio")),
                    c.getString(c.getColumnIndex("data_fine")),
                    c.getInt(c.getColumnIndex("read")),
                    c.getString(c.getColumnIndex("user_ref")),
                )
            )
        }

        return l
    }

    fun allRead(): Long{
        return repository.allRead()
    }


    fun notificationRead(id: Int): Long{
        return repository.notificationRead(id)
    }
}