package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class CategoryCardViewHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {

    val catTitle: TextView
    private val onItemListener: OnItemListener

    init {
        catTitle = itemView.findViewById(R.id.cat_name_itm)
        onItemListener = lister
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onItemListener.onItemClick(adapterPosition)
    }
}