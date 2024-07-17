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

    fun getNotification(userRef: String): List<Notification>{
        val c = repository.getNotification(userRef)
        val l: MutableList<Notification> = arrayListOf()

        while(c.moveToNext()){

            l.add(
                Notification(
                    c.getInt(
                        c.getColumnIndexOrThrow("id")
                    ),
                    c.getString(
                        c.getColumnIndexOrThrow("title")
                    ),
                    c.getString(
                        c.getColumnIndexOrThrow("content")
                    ),
                    c.getString(
                        c.getColumnIndexOrThrow("data_inizio")
                    ),
                    c.getString(
                        c.getColumnIndexOrThrow("data_fine")
                    ),
                    c.getInt(
                        c.getColumnIndexOrThrow("read")
                    ),
                    c.getString(
                        c.getColumnIndexOrThrow("usr_ref")
                    ),
                )
            )
        }

        return l
    }


    fun notificationRead(user_ref: String, id: Int): Int{
        return repository.notificationRead(user_ref, id)
    }

    fun readAllNotification(userRef: String): Int {
        return repository.readAllNotification(userRef)
    }

    fun deleteNotification(id: Int, usrRef: String): Any {
        return repository.deleteNotification(id, usrRef)
    }
}