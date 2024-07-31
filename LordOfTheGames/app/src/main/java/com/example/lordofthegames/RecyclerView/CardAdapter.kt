package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.home.GameItem
import com.example.lordofthegames.home.HomeViewModel
import com.squareup.picasso.Picasso


class CardAdapter(
    var listener: OnItemListener,
    viewModell: HomeViewModel,
    var cardItemList: List<GameItem>?,
    var activity: Activity,
    var mail: String
): RecyclerView.Adapter<CardViewHolder>() {

    private var filteredData: MutableList<GameItem> = cardItemList as MutableList<GameItem> //mutableListOf()
    private var viuvve: HomeViewModel = viewModell

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_item2, parent, false)

        //cardItemList?.forEach { el -> filteredData.add(GameCardItem(el.game_cover, el.game_title)) }

        return CardViewHolder(layoutView, listener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCardItem: GameItem = this.filteredData[position]


        val picasso = Picasso.Builder(activity as Context)
            .loggingEnabled(true) // Abilita il logging per il debug
            .build()
        Log.i(currentCardItem.game.gameTitle, currentCardItem.game.imageResource)
        picasso
            .load(currentCardItem.game.imageResource)
            .resize(150, 100)
            .centerCrop()
            .into(holder.gameImg)

        /*
        var drawable: Drawable? = null

        when((0..2).random()) {
            0 -> drawable = ContextCompat.getDrawable(activity, R.drawable.ic_t_pose)
            1 -> drawable = ContextCompat.getDrawable(activity, R.mipmap.ic_gabibbo_test)//ic_gabibbo_test",
            2 -> drawable = ContextCompat.getDrawable(activity, R.mipmap.ic_yeee_foreground)//ic_yeee_foreground
        }
        holder.gameImg.setImageDrawable(drawable)
        */

        if(viuvve.getGameListValidity(currentCardItem.game.gameTitle, mail)){
            holder.addBtn.visibility = View.GONE
            holder.modifyBtn.visibility = View.GONE
        }


        holder.gameTitle.text = currentCardItem.game.gameTitle


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

        val a = this.viuvve.getAchievementCount(currentCardItem.game.gameTitle, mail)
        Log.e(currentCardItem.game.gameTitle, a.toString())
        holder.achieText.text = "${a.x}/${a.y}"

        holder.modifyBtn.setOnClickListener {
            val i = this.viuvve.newGAmeAdded("Playing", currentCardItem.game.gameId, mail)
            if(i>0) {
                Utilities.showaToast(
                    activity as Context,
                    "Have fun playing ${currentCardItem.game.gameTitle}"
                )
                holder.addBtn.visibility = View.INVISIBLE
                holder.modifyBtn.visibility = View.INVISIBLE
            } else {
                Utilities.showaToast(activity as Context, "Errore nell'inserimento del gioco in lista")
            }
        }

        holder.addBtn.setOnClickListener {
            val i = this.viuvve.newGAmeAdded("Wanted to play", currentCardItem.game.gameId, mail)
            if(i>0){
                Utilities.showaToast(
                    activity as Context,
                    "${currentCardItem.game.gameTitle} added to wishlist"
                )
                holder.addBtn.visibility = View.GONE
                holder.modifyBtn.visibility = View.GONE
            } else {
                Utilities.showaToast(activity as Context, "Errore nell'inserimento del gioco in lista")
            }
        }

        var conta = 0


        currentCardItem.category.forEach { x ->
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
        currentCardItem.platform.forEach { x ->
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


    fun setFilter(newData: Collection<GameItem>) {
        filteredData = ArrayList(newData)

        this.notifyDataSetChanged() // Notifica la RecyclerView che i dati sono cambiati
    }


}