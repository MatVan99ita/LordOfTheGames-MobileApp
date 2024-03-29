package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R

class CategoryCardAdapter(var listener: OnItemListener, var cardItemList: List<CategoryCardItem>): RecyclerView.Adapter<CategoryCardViewHolder>() {
    //var listener: OnItemListener, var cardItemList: List<CategoryCardItem>, var activity: Activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.category_element, parent, false)
        return CategoryCardViewHolder(layoutView, listener)
    }
    override fun onBindViewHolder(holder: CategoryCardViewHolder, position: Int) {
        val currentCardItem: CategoryCardItem = this.cardItemList[position]
        holder.catTitle.text = currentCardItem.category_name
    }
    override fun getItemCount(): Int {
        return cardItemList.size
    }

}