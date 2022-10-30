package com.example.lordofthegames

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
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
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grid_layout);

        setGridView(this)
    }

    private fun setGridView(mainActivity: MainActivity) {

        simpleGrid = findViewById<View>(R.id.simpleGridView) as GridView // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView
        // Create an object of CustomAdapter and set Adapter to GirdView
        val customAdapter = CustomAdapter(applicationContext, logos)
        simpleGrid!!.adapter = customAdapter
        // implement setOnItemClickListener event on GridView
        // implement setOnItemClickListener event on GridView
        simpleGrid!!.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                // set an Intent to Another Activity
                val intent = Intent(mainActivity, SecondActivity::class.java)
                print("PORCODDDIDIDOOOIDODIODIDOI   $position")
                intent.putExtra("image", logos[position]) // put image data in Intent
                startActivity(intent) // start Intent
            }
    }
}
