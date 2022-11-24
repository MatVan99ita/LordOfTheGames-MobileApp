package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.lordofthegames.R

class CardAdapter(var listener: OnItemListener, var cardItemList: List<CardItem>, var activity: Activity): RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return CardViewHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCardItem: CardItem = this.cardItemList[position]
        val imagePath: String = currentCardItem.imageResource

        var drawable: Drawable? = null

        if (imagePath.contains("ic_")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }

        holder.gameImg.setImageDrawable(drawable)

        holder.gameTitle.text = currentCardItem.gameTitle
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

}