package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Pair
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeViewModel

class MyGameAdapter(
    var listener: OnItemListener,
    var viewModel: HomeViewModel,
    var mygamelist: List<MyGameListItem>,
    var activity: Activity,
    var mail: String
) : RecyclerView.Adapter<MyGameAdapter.MyGameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGameHolder {
        return MyGameHolder(LayoutInflater.from(parent.context).inflate(R.layout.mygame_item, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return mygamelist.size ?: 0
    }

    override fun onBindViewHolder(holder: MyGameHolder, position: Int) {
        mygamelist.forEach {
            Log.i("MANNACC", "${it.game_title} - ${it.game_status}")
        }
        val g: MyGameListItem = mygamelist[position]

        holder.id.text = "${g.game_id}"
        holder.nome.text = g.game_title
        holder.stat.text = g.game_status

        //holder.ach.text = "8/8"
        val p: Pair<Int, Int> = viewModel.getAchievementCount(game_title = g.game_title, user_ref = mail)
        holder.ach.text = "${p.x}/${p.y}"
    }

    class MyGameHolder(itemView: View, listener: OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var id: TextView = itemView.findViewById(R.id.mg_id)
        var img: ImageView = itemView.findViewById(R.id.mg_img)
        var nome: TextView = itemView.findViewById(R.id.mg_name)
        var stat: TextView = itemView.findViewById(R.id.mg_stat)
        var ach: TextView = itemView.findViewById(R.id.mg_ach)

        private var onItemListener: OnItemListener

        init {
            onItemListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(itemView, adapterPosition)
        }

    }


}