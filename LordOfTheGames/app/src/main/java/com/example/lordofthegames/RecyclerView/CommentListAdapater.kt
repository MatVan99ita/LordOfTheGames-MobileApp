package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Comments

class CommentListAdapater(var activity: Activity, var listener: OnItemListener, var list: List<Comments>):
    RecyclerView.Adapter<CommentListAdapater.CommentListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListHolder {
        return CommentListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comment_item,
                parent,
                false
            ), listener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CommentListHolder, position: Int) {
        val item: Comments = list[position]

        holder.comment_content.text = item.content
        holder.down_count.text = "${item.comment_dislike}"
        holder.up_count.text = "${item.comment_like}"
        holder.user_nick.text = item.user_ref


    }


    fun updateView(discussionComments: List<Comments>) {
        list = discussionComments
        // Aggiorna l'adapter con la lista filtrata
        this.notifyDataSetChanged()
    }



    inner class CommentListHolder(itemView: View, lister: OnItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val user_nick: TextView = itemView.findViewById(R.id.user_nick)
        val comment_content: TextView = itemView.findViewById(R.id.comment_content)
        val down_count: TextView = itemView.findViewById(R.id.down_count)
        val up_count: TextView = itemView.findViewById(R.id.up_count)
        val up_btn:

        private val onItemListener: OnItemListener = lister

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
            p0?.id
                ?.let { activity.resources.getResourceEntryName(it) }
                ?.let { Log.i("IPPONEP", it) }
        }
    }
}
