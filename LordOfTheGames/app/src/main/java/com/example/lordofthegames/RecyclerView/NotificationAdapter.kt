package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.home.HomeViewModel
import com.example.lordofthegames.home.NotificationViewModel
import com.google.android.material.button.MaterialButton

class NotificationAdapter(var listener: OnItemListener,
                          var viewModel: NotificationViewModel,
                          var notification_list: List<Notification>,
                          var activity: Activity
): RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        return NotificationHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mygame_item, parent, false),
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
    }

    inner class NotificationHolder(itemView: View, listener: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val title: TextView = itemView.findViewById(R.id.notification_title)
        val content: TextView = itemView.findViewById(R.id.notification_content)
        val mbtn: MaterialButton = itemView.findViewById(R.id.edit_btn)
        private var onItemListener: OnItemListener = listener


        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }

    }
}