package com.example.lordofthegames.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.google.android.material.button.MaterialButton

class CardViewHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val gameImg: ImageView
    val gameTitle: TextView
    val achieText: TextView

    val modifyBtn: MaterialButton
    val addBtn: MaterialButton

    private val onItemListener: OnItemListener

    init {
        this.gameImg = itemView.findViewById(R.id.game_img)
        this.gameTitle = itemView.findViewById(R.id.game_title2)
        this.achieText = itemView.findViewById(R.id.achieve)
        this.modifyBtn = itemView.findViewById(R.id.edit_btn)
        this.addBtn = itemView.findViewById(R.id.add_btn)

        this.onItemListener = lister
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onItemListener.onItemClick(itemView, adapterPosition)
    }
}