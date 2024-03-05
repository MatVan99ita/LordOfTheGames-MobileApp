package com.example.lordofthegames.recyclerView

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
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

        holder.parentPlat.setCardBackgroundColor(Color.rgb(24, 128, 24)/*currentCardItem.color*/)
        val contrast = ColorUtils.calculateContrast(Color.BLACK, currentCardItem.color)
        if(contrast <= 2f){
            holder.platTitle.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }
}