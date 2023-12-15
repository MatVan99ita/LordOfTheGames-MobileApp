package com.example.lordofthegames.recyclerView

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

interface OnItemListener {

    fun onItemClick(view: View, position: Int)
}