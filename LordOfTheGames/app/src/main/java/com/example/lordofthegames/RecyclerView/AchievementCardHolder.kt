package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class AchievementCardHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val achievementImg: ImageView
    private val onItemListener: OnItemListener

    init {
        this.achievementImg = itemView.findViewById(R.id.game_img)
        this.onItemListener = lister
        itemView.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        onItemListener.onItemClick(adapterPosition)
    }
}