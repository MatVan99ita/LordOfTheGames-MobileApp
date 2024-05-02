package com.example.lordofthegames.Community

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.db_entities.Comments
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
                        discussion_id = c.getInt(c.getColumnIndexOrThrow("discussion_id")),
                        title = c.getString(c.getColumnIndexOrThrow("title")),
                        content = c.getString(c.getColumnIndexOrThrow("content")),
                        game_ref = c.getInt(c.getColumnIndexOrThrow("game_id")),
                        user_ref = c.getString(c.getColumnIndexOrThrow("user_ref"))
                    ),
                    c.getInt(c.getColumnIndexOrThrow("TotaleLike")),
                    c.getInt(c.getColumnIndexOrThrow("NumeroCommenti")),
                )
            )
        }
        return l
    }

    /**Cursor(comment_id, content, comment_like, comment_dislike)*/
    fun getDiscussionComments(discussion_id: Int):List<Comments>{

        val c = repo.selectCommentFromDiscussion(discussion_id)
        val l = arrayListOf<Comments>()
        while(c.moveToNext()){
            l.add(
                Comments(
                    comment_id = c.getInt(c.getColumnIndexOrThrow("comment_id")),
                    discussion_ref = discussion_id,
                    content = c.getString(c.getColumnIndexOrThrow("content")),
                    comment_like = c.getInt(c.getColumnIndexOrThrow("comment_like")),
                    comment_dislike = c.getInt(c.getColumnIndexOrThrow("comment_dislike")),
                    user_ref = c.getString(c.getColumnIndexOrThrow("user_ref"))
                )
            )
        }

        return l
    }

}