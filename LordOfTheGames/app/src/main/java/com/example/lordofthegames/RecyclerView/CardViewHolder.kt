package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class CardViewHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val gameImg: ImageView
    val gameTitle: TextView
    private val onItemListener: OnItemListener

    init {
        this.gameImg = itemView.findViewById(R.id.game_img)
        this.gameTitle = itemView.findViewById(R.id.game_title2)
        this.onItemListener = lister
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onItemListener.onItemClick(adapterPosition)
    }
}