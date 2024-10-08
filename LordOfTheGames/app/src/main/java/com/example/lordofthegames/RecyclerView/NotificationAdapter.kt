package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.home.NotificationViewModel
import com.google.android.material.button.MaterialButton

class NotificationAdapter(
    var listener: OnItemListener,
    var notification_list: List<Notification>,
    var activity: Activity,
): RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        return NotificationHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_obj, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return notification_list.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val item = notification_list[position]
        holder.title.text = item.title
        holder.content.text
        if(item.read == 0){
            holder.point.visibility = View.VISIBLE
            holder.body.setBackgroundResource(R.drawable.rainbow)
        } else {
            holder.point.visibility = View.GONE
            holder.body.setBackgroundResource(R.drawable.raonbow2)
        }
    }

    fun updateView(new_set: List<Notification>) {
        this.notification_list = new_set
        this.notifyDataSetChanged()
    }


    inner class NotificationHolder(itemView: View, listener: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val title: TextView = itemView.findViewById(R.id.notification_title)
        val content: TextView = itemView.findViewById(R.id.notification_content)
        val point: ImageView = itemView.findViewById(R.id.check_notification_point)
        val body: ConstraintLayout = itemView.findViewById(R.id.notification_body)
        private var onItemListener: OnItemListener = listener

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }

    }
}