package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Community.DiscussionViewModel
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.Comments

class CommentListAdapater(var activity: Activity, var listener: OnItemListener, var list: List<Comments>, var viewm: DiscussionViewModel):
    RecyclerView.Adapter<CommentListAdapater.CommentListHolder>() {

    private var like_inserted=0
    private var dislike_inserted = 0

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

        holder.up_btn.setOnClickListener {
            if(like_inserted > 0){

                if(viewm.deUpComment(item.comment_id) < 0){
                    Utilities.showaToast(activity, "Error")
                } else {
                    like_inserted--
                    item.comment_like = item.comment_like!! - 1
                    holder.up_count.text = "${item.comment_like}"

                    holder.up_btn.clearColorFilter()
                }
            } else {

                if (viewm.upComment(item.comment_id) < 0) {
                    Utilities.showaToast(
                        activity,
                        "Cannot Like comment"
                    )
                }
                else {
                    like_inserted++
                    item.comment_like = item.comment_like!! + 1
                    holder.up_count.text = "${item.comment_like}"
                    holder.up_btn.setColorFilter(
                        ContextCompat.getColor(
                            activity,
                            R.color.purple_500
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }

        }

        holder.down_btn.setOnClickListener {
            if(viewm.dowmComment(item.comment_id) < 0) Utilities.showaToast(activity, "Cannot Dislike comment")
            else {
                dislike_inserted++
                item.comment_dislike = item.comment_dislike!! + 1
                holder.down_count.text = "${item.comment_dislike}"
            }
        }

    }


    fun updateView(discussionComments: List<Comments>) {
        list = discussionComments
        // Aggiorna l'adapter con la lista filtrata
        this.notifyDataSetChanged()
    }



    inner class CommentListHolder(itemView: View, lister: OnItemListener) :
        RecyclerView.ViewHolder(itemView){

        val user_nick: TextView = itemView.findViewById(R.id.user_nick)
        val comment_content: TextView = itemView.findViewById(R.id.comment_content)
        val down_count: TextView = itemView.findViewById(R.id.down_count)
        val up_count: TextView = itemView.findViewById(R.id.up_count)

         val up_btn: ImageButton = itemView.findViewById(R.id.up_img)
         val down_btn: ImageButton = itemView.findViewById(R.id.down_img)
    }
}
