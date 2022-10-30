package com.example.lordofthegames

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var simpleGrid: GridView? = null
    var logos = intArrayOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grid_layout);

        //setRecyclerView(this)
    }

    /*lateinit var adapter: CardAdapter
    lateinit var recyclerView: RecyclerView
    private fun setRecyclerView(mainActivity: Activity) {
        recyclerView = mainActivity.findViewById(R.id.home)
        recyclerView.setHasFixedSize(true)
        val list: List<CardItem> = listOf(CardItem("ic_baseline_android_24", "GIUOCO"))
        adapter = CardAdapter(list, mainActivity)
        recyclerView.adapter = adapter
    }*/
}