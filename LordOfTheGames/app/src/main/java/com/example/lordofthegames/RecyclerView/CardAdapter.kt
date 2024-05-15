package com.example.lordofthegames.recyclerView

import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeViewModel


class CardAdapter(var listener: OnItemListener, viewModell: HomeViewModel, var cardItemList: List<GameCardItem>?, var activity: Activity): RecyclerView.Adapter<CardViewHolder>() {

    private var filteredData: MutableList<GameCardItem> =
        cardItemList as MutableList<GameCardItem> //mutableListOf()
    private lateinit var viuvve: HomeViewModel;
    init {
        viuvve = viewModell
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)

        //cardItemList?.forEach { el -> filteredData.add(GameCardItem(el.game_cover, el.game_title)) }

        return CardViewHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCardItem: GameCardItem = this.filteredData[position]
        val imagePath: String = currentCardItem.imageResource
        //val catItems: MutableList<CategoryCardItem> = mutableListOf()
        //val platItems: MutableList<PlatformCardItem> = mutableListOf()

        var drawable: Drawable? = null

        when((0..2).random()) {
            0 -> drawable = ContextCompat.getDrawable(activity, R.drawable.ic_t_pose)
            1 -> drawable = ContextCompat.getDrawable(activity, R.mipmap.ic_gabibbo_test)//ic_gabibbo_test",
            2 -> drawable = ContextCompat.getDrawable(activity, R.mipmap.ic_yeee_foreground)//ic_yeee_foreground
        }
        if(!viuvve.getGameListValidity(currentCardItem.gameTitle)){
            holder.addBtn.visibility = View.GONE
            holder.modifyBtn.visibility = View.GONE
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

        val catItems = this.viuvve.getGameCategory(currentCardItem.gameTitle)
        val platItems = this.viuvve.getGamePlatform(currentCardItem.gameTitle)
        val a = this.viuvve.getAchievementCount(currentCardItem.gameTitle)
        holder.achieText.text = "${a.x}/${a.y}"

        holder.modifyBtn.setOnClickListener {
            this.viuvve.modifyGameStatus("Playing", currentCardItem.gameId)
            Utilities.showaToast(activity as Context, "Have fun playing ${currentCardItem.gameTitle}")
            holder.addBtn.visibility = View.GONE
            holder.modifyBtn.visibility = View.GONE
        }

        holder.addBtn.setOnClickListener {
            this.viuvve.modifyGameStatus("Wanted to play", currentCardItem.gameId)
            Utilities.showaToast(activity as Context, "${currentCardItem.gameTitle} added to wishlist")
            holder.addBtn.visibility = View.GONE
            holder.modifyBtn.visibility = View.GONE
        }

        //val catItems: MutableList<CategoryCardItem> = listOf(
        //    CategoryCardItem("GDR"),
        //    CategoryCardItem("Terza persona"),
        //    CategoryCardItem("JRPG"),
        //    CategoryCardItem("JRPG"),
        //    CategoryCardItem("JRPG"),
        //    CategoryCardItem("JRPG"),
        //    CategoryCardItem("JRPG")
        //) as MutableList<CategoryCardItem>
        //val platItems: MutableList<PlatformCardItem> = listOf(
        //    PlatformCardItem("PS4", Color.rgb(19, 44, 116)),
        //    PlatformCardItem("STEAM", Color.rgb(41, 41, 41)),
        //    PlatformCardItem("EPIC", Color.rgb(58, 58, 56)),
        //    PlatformCardItem("XBOX ONE", Color.rgb(24, 128, 24)),
        //    PlatformCardItem("Game Pass", Color.rgb(24, 128, 24)),
        //    PlatformCardItem("Nintendo", Color.rgb(231, 8, 25))
        //) as MutableList<PlatformCardItem>




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
                t.setBackgroundColor(x.color)
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


}