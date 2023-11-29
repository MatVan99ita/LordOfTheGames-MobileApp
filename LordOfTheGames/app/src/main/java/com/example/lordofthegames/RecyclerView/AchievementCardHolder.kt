package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class AchievementCardHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val achievementImg: ImageView
    val title: TextView
    val completation: TextView
    private val onItemListener: OnItemListener

    init {
        this.achievementImg = itemView.findViewById(R.id.achievement_item_img)
        this.title = itemView.findViewById(R.id.achievement_title)
        this.completation = itemView.findViewById(R.id.achievement_completetion)
        this.onItemListener = lister
        itemView.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        onItemListener.onItemClick(adapterPosition)
    }
}