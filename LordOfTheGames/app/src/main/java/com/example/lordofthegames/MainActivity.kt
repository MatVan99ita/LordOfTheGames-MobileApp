package com.example.lordofthegames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.lordofthegames.GridView.CustomAdapter
import com.example.lordofthegames.GridView.SecondActivity


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

        simpleGrid = findViewById<View>(R.id.simpleGridView) as GridView // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView
        // Create an object of CustomAdapter and set Adapter to GirdView
        val customAdapter = CustomAdapter(applicationContext, logos)
        simpleGrid!!.adapter = customAdapter
        // implement setOnItemClickListener event on GridView
        // implement setOnItemClickListener event on GridView
        simpleGrid!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                // set an Intent to Another Activity
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("image", logos[position]) // put image data in Intent
                startActivity(intent) // start Intent
            }

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
