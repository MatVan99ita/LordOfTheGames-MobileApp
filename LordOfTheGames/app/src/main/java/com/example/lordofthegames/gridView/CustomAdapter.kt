package com.example.lordofthegames.gridView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.lordofthegames.HomeFragment
import com.example.lordofthegames.R
import com.example.lordofthegames.recyclerView.CardItem
import kotlinx.coroutines.NonDisposableHandle.parent

class CustomAdapter(
    private val context: Context,
    private val games: List<CardItem>,
    private val activity: Activity
):
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    private lateinit var iconTitle: TextView
    private lateinit var icon: ImageView

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

        println("BELANDIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII: $this")
        Log.e("BANANANAN", games[i].toString())

        var drawable: Drawable? = null
        var convertView = view
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        }

        icon      = convertView!!.findViewById(R.id.game_img)
        iconTitle = convertView  .findViewById(R.id.game_title2)

        // on below line we are setting image for our course image view.
        val el = this.games[i]
        val imagePath: String = el.imageResource
        if (imagePath.contains("ic_")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }
        icon.setImageDrawable(drawable)
        iconTitle.text = el.gameTitle
        return convertView
    }

}