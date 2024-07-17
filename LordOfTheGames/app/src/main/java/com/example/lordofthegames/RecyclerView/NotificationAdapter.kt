package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Notification
import com.google.android.material.button.MaterialButton

class NotificationAdapter(
    var listener: OnItemListener,
    var notification_list: List<Notification>,
    var activity: Activity
): RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    //TODO: creare la funzione per rendere lette le notifiche
    //      il cestino deve rimuovere la notifica dal db e anche dalla vista

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
        }
    }

    inner class NotificationHolder(itemView: View, listener: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val title: TextView = itemView.findViewById(R.id.notification_title)
        val content: TextView = itemView.findViewById(R.id.notification_content)
        val point: ImageView = itemView.findViewById(R.id.check_notification_point)
        val cestino: MaterialButton = itemView.findViewById(R.id.edit_btn)
        val body: ConstraintLayout = itemView.findViewById(R.id.notification_body)
        private var onItemListener: OnItemListener = listener


        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }

    }
}