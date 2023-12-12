package com.example.lordofthegames.recyclerView

import android.R.attr.data
import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game


class CardAdapter(var listener: OnItemListener, var cardItemList: List<GameCardItem>, private var catItems: List<CategoryCardItem>, private var platItems: List<PlatformCardItem>, var activity: Activity): RecyclerView.Adapter<CardViewHolder>() {


    private var originalData: MutableList<GameCardItem> = cardItemList as MutableList<GameCardItem>
    private var filteredData: MutableList<GameCardItem> = ArrayList(cardItemList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)
        return CardViewHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCardItem: GameCardItem = this.filteredData[position]
        val imagePath: String = currentCardItem.imageResource

        var drawable: Drawable? = null

        if (imagePath.contains("ic_")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
        } else if(imagePath.contains("gabibbo")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_gabibbo_test", "mipmap", activity.packageName))
        } else if(imagePath.contains("yee")) {
            drawable = ContextCompat.getDrawable(activity, activity.resources.getIdentifier("ic_yeee_foreground", "mipmap", activity.packageName))
        }

        holder.gameImg.setImageDrawable(drawable)

        holder.gameTitle.text = currentCardItem.gameTitle


        val catList: MutableList<TextView> = mutableListOf()
        val platList: MutableList<TextView> = mutableListOf()

        val listCat = holder.itemView.findViewById<LinearLayout>(R.id.category_linear_home)
        listCat.removeAllViews()
        val listPlat = holder.itemView.findViewById<LinearLayout>(R.id.platform_linear_home)
        listPlat.removeAllViews()


        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 5, 0)
        var conta = 0
        catItems.forEach { x ->
            if(conta < 3){
                val t = TextView(holder.itemView.context)
                t.text = x.category_name
                t.setBackgroundColor(activity.resources.getColor(R.color.green_light_variant))
                t.layoutParams = lp
                t.setPadding(5, 5, 5, 5)
                catList.add(t)
                conta++
            }
        }
        conta = 0
        platItems.forEach { x ->
            if(conta < 3){
                val t = TextView(holder.itemView.context)
                t.text = x.platFormName
                t.setBackgroundColor(activity.resources.getColor(R.color.green_light_variant))
                t.layoutParams = lp
                t.setPadding(5, 5, 5, 5)
                platList.add(t)
                conta++
            }
        }

        catList.forEach { el ->
            listCat.addView(el)
        }

        platList.forEach { el ->
            listPlat.addView(el)
        }

    }

    override fun getItemCount(): Int {
        return filteredData.size
    }


    fun setFilter(newData: Collection<GameCardItem>) {
        filteredData = ArrayList(newData)

        this.notifyDataSetChanged() // Notifica la RecyclerView che i dati sono cambiati
    }

    fun update(list: List<TextView>){

    }

}