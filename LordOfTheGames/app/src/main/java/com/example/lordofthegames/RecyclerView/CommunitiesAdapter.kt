package com.example.lordofthegames.recyclerView

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R

class CommunitiesAdapter(var activity: Activity, var listener: OnItemListener, var cardItemList: List<CommunityItem>): RecyclerView.Adapter<CommunitiesAdapter.CommunitiesHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesHolder {
        return CommunitiesHolder(LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.community_item,
                parent,
                false
            ), listener)
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

    override fun onBindViewHolder(holder: CommunitiesHolder, position: Int) {
        val c: CommunityItem = cardItemList[position]

        holder.communityTitle.text = c.GameTitle
        holder.heart_count.text = "${c.TotalLike}"

        if (c.TotalLike < 0)
            holder.heart_count.setTextColor( Color.Red.toArgb() )

        holder.discussion_count.text = "${c.TotalDiscussions}"



        val drawable: Drawable? = when((0..2).random()) {
            0 -> ContextCompat.getDrawable(activity, R.drawable.ic_t_pose)
            1 -> ContextCompat.getDrawable(activity, R.mipmap.ic_gabibbo_test)//ic_gabibbo_test",
            2 -> ContextCompat.getDrawable(activity, R.mipmap.ic_yeee_foreground)//ic_yeee_foreground
            else -> null
        }
        holder.communityImg.setImageDrawable(drawable)


        holder.layout.setBackgroundColor( when((0..11).random()) {
            0   -> R.color.rosso
            1   -> R.color.arancione
            2   -> R.color.giallo
            3   -> R.color.verde1
            4   -> R.color.verde2
            5   -> R.color.verde3
            6   -> R.color.azzurro1
            7   -> R.color.azzurro2
            8   -> R.color.blu
            9   -> R.color.viola1
            10  -> R.color.viola2
            11  -> R.color.viola3
            else -> Color.White.toArgb()
        })



    }


    inner class CommunitiesHolder(itemView: View, lister: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var communityImg: ImageView = itemView.findViewById(R.id.community_img)
        var communityTitle: TextView = itemView.findViewById(R.id.community_game_title)
        var heart_count: TextView = itemView.findViewById(R.id.heart_count)
        var discussion_count: TextView = itemView.findViewById(R.id.discussion_count)
        var layout: ConstraintLayout = itemView.findViewById(R.id.community_body)

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }


}