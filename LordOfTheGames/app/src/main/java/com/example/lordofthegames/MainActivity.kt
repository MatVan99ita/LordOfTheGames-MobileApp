package com.example.lordofthegames

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.RecyclerView.CardAdapter
import com.example.lordofthegames.RecyclerView.CardItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home);

        setRecyclerView(this)
    }

    lateinit var adapter: CardAdapter
    lateinit var recyclerView: RecyclerView
    private fun setRecyclerView(mainActivity: Activity) {
        recyclerView = mainActivity.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val list: List<CardItem> = listOf(CardItem("ic_baseline_android_24", "GIUOCO"))
        adapter = CardAdapter(list, mainActivity)
        recyclerView.adapter = adapter
    }
}