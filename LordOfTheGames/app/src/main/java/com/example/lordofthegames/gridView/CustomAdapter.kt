package com.example.lordofthegames.gridView

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
import com.example.lordofthegames.games.Game
import com.example.lordofthegames.R

class CustomAdapter(var context: Context, var games: List<Game>, var activity: Activity) : BaseAdapter() {
    private val inflater: LayoutInflater = (LayoutInflater.from(context))

    override fun getCount(): Int {
        return games.count()
    }

    override fun getItem(p0: Int): Any {
        return games[p0]
    }

    override fun getItemId(p0: Int): Long {
        return games.indexOf(this.getItem(p0)).toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_item, null)
        val icon: ImageView = view!!.findViewById(R.id.icon)
        val iconTitle: TextView = view.findViewById(R.id.title)
        val el = this.games[i]
        iconTitle.text = this.games[i].name
        var drawable: Drawable? = null
        val imagePath: String = el.image

        if (imagePath.contains("ic_")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }
        icon.setImageDrawable(drawable)
        return view
    }

}