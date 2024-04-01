package com.example.lordofthegames.home

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import com.example.lordofthegames.Database.LotgRepo
import com.example.lordofthegames.recyclerView.CommunityItem

class CommunityViewModel(application: Application): AndroidViewModel(application) {

    private val repo: LotgRepo = LotgRepo(application)

    /** Cursor(GameTitle, TotalDiscussion, TotalLike) */
    fun selectAllCommunity(): List<CommunityItem> {

        val c = repo.selectAllCommunity()
        val l = arrayListOf<CommunityItem>()
        while(c.moveToNext()){
            l.add(
                CommunityItem(
                    c.getString(c.getColumnIndexOrThrow("GameTitle")),
                    c.getInt(c.getColumnIndexOrThrow("TotalDiscussions")),
                    c.getInt(c.getColumnIndexOrThrow("TotalLike"))
                )
            )
        }
        return l
    }



    /**Cursor(comment_id, content, comment_like, comment_dislike)*/
    fun selectCommentFromDiscussion(discussion_id: Int): Cursor {
        return repo.selectCommentFromDiscussion(discussion_id)
    }


}