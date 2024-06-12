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
        val l: MutableList<Notification> = arrayListOf()

        while(c.moveToNext()){

            l.add(
                Notification(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("content")),
                    c.getString(c.getColumnIndexOrThrow("data_inizio")),
                    c.getString(c.getColumnIndexOrThrow("data_fine")),
                    c.getInt(c.getColumnIndexOrThrow("read")),
                    c.getString(c.getColumnIndexOrThrow("usr_ref")),
                )
            )
        }

        return l
    }

    fun allRead(): Int{
        return repository.allRead()
    }


    fun notificationRead(id: Int): Int{
        return repository.notificationRead(id)
    }
}