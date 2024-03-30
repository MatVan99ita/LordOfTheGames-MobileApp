package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }



    inner class DiscussionSpecificHolder(itemView: View, lister: OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }
}