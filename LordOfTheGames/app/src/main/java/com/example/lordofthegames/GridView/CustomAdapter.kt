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
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.lordofthegames.Games.Game
import com.example.lordofthegames.R
import com.example.lordofthegames.RecyclerView.CardItem
import com.example.lordofthegames.RecyclerView.CardViewHolder

class CustomAdapter(var context: Context, var games: List<Game>, var activity: Activity) : BaseAdapter() {
    private val inflater: LayoutInflater = (LayoutInflater.from(context))

    override fun getCount(): Int {
        return games.count()
    }

    override fun getItem(p0: Int): Any? {
        return games[p0]
    }

    override fun getItemId(p0: Int): Long {
        return games.indexOf(this.getItem(p0)).toLong()
    }

    //dhvcblawdivgahkjbvnlhaiykjgrm ,hygvj

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_item, null)
        val icon: ImageView = view!!.findViewById(R.id.icon)
        val icon_title: TextView = view.findViewById(R.id.title)
        val el = this.games[i]
        icon_title.text = this.games[i].name
        println("BESUGHI   " + el.image)
        var drawable: Drawable? = null
        val imagePath: String = el.image

        if (imagePath.contains("ic_")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")){
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }
        icon.setImageDrawable(drawable)
        return view
    }

}