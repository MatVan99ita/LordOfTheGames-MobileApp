package com.example.lordofthegames.GridView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Games.Game
import com.example.lordofthegames.R
import com.example.lordofthegames.RecyclerView.CardItem
import com.example.lordofthegames.RecyclerView.CardViewHolder

class CustomAdapter(var games: List<Game>, var activity: Activity) : RecyclerView.Adapter<GameViewHolder>(){

    override fun getItemId(p0: Int): Long {
        return games.indexOf(this.games[p0]).toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout,
        parent, false
            )
        return GameViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = games[position]
        val imagePath: String = game.image
        var drawable: Drawable? = null
        if (imagePath.contains("ic_")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }
        holder.img.setImageDrawable(drawable)
        holder.title.text = game.name
    }

    override fun getItemCount(): Int {
        return games.size
    }

}