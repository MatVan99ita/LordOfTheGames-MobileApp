package com.example.lordofthegames.Community

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.DiscussionItem

class DiscussionViewModel(application: Application): AndroidViewModel(application) {

    private val repo: LotgRepo = LotgRepo(application)

    /**List<DiscusssionItem(Discussion(id, title, content, game_id), TotalLike, NumeroCommenti)>*/
    fun selectAllDiscussion(game_title: String): List<DiscussionItem> {


        val c = repo.selectAllDiscussion(game_title)
        val l = arrayListOf<DiscussionItem>()
        while (c.moveToNext()){
            l.add(
                DiscussionItem(
                    Discussion(
                        c.getInt(c.getColumnIndexOrThrow("discussion_id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("content")),
                        c.getInt(c.getColumnIndexOrThrow("game_id"))
                    ),
                    c.getInt(c.getColumnIndexOrThrow("TotaleLike")),
                    c.getInt(c.getColumnIndexOrThrow("NumeroCommenti")),
                )
            )
        }
        l.forEach { it -> Log.i("PORCODDIO", it.toString()) }
        return l
    }
}