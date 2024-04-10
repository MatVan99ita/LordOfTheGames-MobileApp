package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class DiscussionSpecificAdapter(var activity: Activity, var listener: OnItemListener, var list: List<DiscussionItem>):
    RecyclerView.Adapter<DiscussionSpecificAdapter.DiscussionSpecificHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussionSpecificHolder {
        return DiscussionSpecificHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.discussion_item,
                parent,
                false
            ), listener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DiscussionSpecificHolder, position: Int) {
        val item: DiscussionItem = list[position]

        holder.discussion_title.text = item.discussion.title
        holder.discussion_content.text = item.discussion.content
        holder.discussion_comments.text = "${item.totComment}"
        holder.discussion_likes.text = "${item.totaleLike}"

    }


    inner class DiscussionSpecificHolder(itemView: View, lister: OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var discussion_title: TextView= itemView.findViewById(R.id.discussion_title)
        var discussion_content: TextView = itemView.findViewById(R.id.discussion_content)
        var discussion_likes: TextView = itemView.findViewById(R.id.comment_count)
        var discussion_comments: TextView = itemView.findViewById(R.id.like_count)
        private val onItemListener: OnItemListener = lister

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }
    }
}