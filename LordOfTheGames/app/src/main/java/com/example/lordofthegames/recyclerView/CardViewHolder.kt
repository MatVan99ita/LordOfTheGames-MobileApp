package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gameImg: ImageView
    val gameTitle: TextView

    init {
        this.gameImg = itemView.findViewById(R.id.gameImage)
        this.gameTitle = itemView.findViewById(R.id.game_title)
    }
}