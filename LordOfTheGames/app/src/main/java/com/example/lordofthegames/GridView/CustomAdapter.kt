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

<<<<<<< HEAD
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
=======
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_item, null)
        val icon: ImageView = view!!.findViewById(R.id.icon)
        val icon_title: TextView = view.findViewById(R.id.title)
        val el = this.games[i]
        icon_title.text = this.games[i].name
        println("BESUGHI   " + el.image)

        val imagePath: String = el.image
>>>>>>> parent of 3e1b4ed (sistemato il grid layout e il second)
        if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            icon.setImageDrawable(drawable)
        } else {
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
            icon.setImageDrawable(drawable)
        }
<<<<<<< HEAD
        holder.img.setImageDrawable(drawable)
        holder.title.text = game.name
    }

    override fun getItemCount(): Int {
        return games.size
=======
        return view
>>>>>>> parent of 3e1b4ed (sistemato il grid layout e il second)
    }

}