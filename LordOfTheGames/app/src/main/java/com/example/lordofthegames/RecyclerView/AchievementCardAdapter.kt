package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Achievement
import com.squareup.picasso.Picasso

class AchievementCardAdapter(var listener: OnItemListener, var cardItemList: List<AchievementCardItem>, var activity: Activity): RecyclerView.Adapter<AchievementCardHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementCardHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.achievement_list_item, parent, false)
        return AchievementCardHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: AchievementCardHolder, position: Int) {

        val currentCardItem = this.cardItemList[position]
        val picasso = Picasso.Builder(activity as Context)
            .loggingEnabled(true) // Abilita il logging per il debug
            .build()
        picasso
            .load(currentCardItem.img)
            .resize(150, 100)
            .centerCrop()
            .into(holder.achievementImg)

        holder.title.text = currentCardItem.name
        holder.completation.text = "${currentCardItem.actual_count}/${currentCardItem.total_count}"

        if(currentCardItem.completed == 1){
            holder.checkImg.visibility = View.VISIBLE
        }
        val drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("yo_listen_foreground", "mipmap", activity.packageName))

        holder.achievementImg.setImageDrawable(drawable)

    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

    fun updateActualCount(position: Int, num: Int) {
        val currentCardItem = this.cardItemList[position]
        currentCardItem.actual_count = num
    }

}