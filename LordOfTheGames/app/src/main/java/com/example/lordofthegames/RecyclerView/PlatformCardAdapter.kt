package com.example.lordofthegames.recyclerView

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import okhttp3.internal.toHexString

class PlatformCardAdapter(var listener: OnItemListener, var cardItemList: List<PlatformCardItem>) : RecyclerView.Adapter<PlatformCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.platform_item, parent, false)
        return PlatformCardViewHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: PlatformCardViewHolder, position: Int) {
        val currentCardItem: PlatformCardItem = this.cardItemList[position]
        Log.i("PORCO", cardItemList[position].toString())
        holder.platTitle.text = currentCardItem.platFormName

        //val colore = Color.toArgb(currentCardItem.color.toLong())

        holder.parentPlat.setCardBackgroundColor(currentCardItem.color)
        val contrast = ColorUtils.calculateContrast(
            Color.BLACK,
            Color.argb(
                255,
                currentCardItem.color.red,
                currentCardItem.color.green,
                currentCardItem.color.blue
            )
        )
        if(contrast <= 2f){
            holder.platTitle.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }
}