package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.databinding.CommentItmBinding
import com.example.lordofthegames.db_entities.Comments

class CommentCardAdapter(
    var listener: OnItemListener,
    var cardItemList: List<Comments>,
    var user: String,
    var activity: Activity
) : RecyclerView.Adapter<CommentCardAdapter.CommentCardHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentCardHolder {
        return CommentCardHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comment_itm,
                parent,
                false
            ), listener
        )
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

    override fun onBindViewHolder(holder: CommentCardHolder, position: Int) {
        val item: CommentItem = cardItemList[position]

        holder.op.visibility = if(item.user == user) View.VISIBLE else View.GONE
        holder.user_name.text = item.user
        holder.comment_content.text = item.content
        holder.likes.text = "${item.like}"
        holder.dislikes.text = "${item.dislike}"
    }


    inner class CommentCardHolder(itemView: View, lister: OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val user_name:       TextView = itemView.findViewById(R.id.user_name)
        val op:              TextView = itemView.findViewById(R.id.op)
        val comment_content: TextView = itemView.findViewById(R.id.comment_content)
        val dislikes:        TextView = itemView.findViewById(R.id.dislike_count)
        val likes:           TextView = itemView.findViewById(R.id.like_count2)

        val onItemListener: OnItemListener = lister

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }
    }

}
