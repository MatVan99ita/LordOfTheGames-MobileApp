package com.example.lordofthegames.RecyclerView

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.lordofthegames.R

class CardAdapter(var cardItemList: List<CardItem>, var activity: Activity): RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCardItem: CardItem = this.cardItemList.get(position)
        val imagePath: String = currentCardItem.imageResource

        if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            holder.gameImg.setImageDrawable(drawable)
        }

        holder.gameTitle.setText(currentCardItem.gameTitle)
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

}