package com.example.lordofthegames.GridView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lordofthegames.R

class CustomAdapter(var context: Context, var logos: IntArray) : BaseAdapter() {
    private val inflater: LayoutInflater = (LayoutInflater.from(context))

    override fun getCount(): Int {
        return logos.count()
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_item, null)
        val icon: ImageView = view!!.findViewById(R.id.icon)
        icon.setImageResource(logos[i])
        return view
    }
}