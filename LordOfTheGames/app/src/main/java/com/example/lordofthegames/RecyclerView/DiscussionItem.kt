package com.example.lordofthegames.recyclerView

import com.example.lordofthegames.db_entities.Discussion


class DiscussionItem(val discussion: Discussion, val totaleLike: Int, val totComment: Int){

    override fun toString(): String {
        return "[discussion: $discussion, totLike: $totaleLike, totComm: $totComment"
    }
}

