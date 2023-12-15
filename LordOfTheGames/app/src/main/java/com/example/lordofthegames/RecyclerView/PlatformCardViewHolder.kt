package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class PlatformCardViewHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val platTitle: TextView
    val parentPlat: CardView
    private val onItemListener: OnItemListener

    init {
        platTitle = itemView.findViewById(R.id.plat_name_itm)
        parentPlat = itemView.findViewById(R.id.platform_item)
        onItemListener = lister
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onItemListener.onItemClick(itemView, adapterPosition)
    }
}