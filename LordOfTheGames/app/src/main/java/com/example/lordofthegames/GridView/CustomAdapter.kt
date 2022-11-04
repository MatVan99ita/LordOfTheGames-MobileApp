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

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_item, null)
        val icon: ImageView = view!!.findViewById(R.id.icon)
        var el = this.games[i]

        println("BESUGHI   " + el.image)

        val imagePath: String = el.image
        if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            icon.setImageDrawable(drawable)
        }
        return view
    }

}