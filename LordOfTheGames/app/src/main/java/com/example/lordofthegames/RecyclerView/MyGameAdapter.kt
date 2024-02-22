package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeViewModel

class MyGameAdapter(
    var listener: OnItemListener,
    viewModel: HomeViewModel,
    var mygamelist: List<Game>?,
    var activity: Activity
) : RecyclerView.Adapter<MyGameAdapter.MyGameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGameHolder {
        return MyGameHolder(LayoutInflater.from(parent.context).inflate(R.layout.mygame_item, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return mygamelist?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyGameHolder, position: Int) {
        val g: Game? = mygamelist?.get(position)

        if (g != null) {
            holder.id.text = "${g.game_id}"
            holder.nome.text = g.game_title
            holder.stat.text = g.game_status
            holder.ach.text = "8/8"
        }
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
            onItemListener.onItemClick(itemView, adapterPosition);
        }

    }
}