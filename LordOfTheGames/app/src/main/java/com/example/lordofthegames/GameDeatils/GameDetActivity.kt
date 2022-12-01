package com.example.lordofthegames.GameDeatils

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.home.HomeFragment


class GameDetActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gamedet)

        val intent: Intent = intent
        val bundle = Bundle()
        val imagePath = intent.getStringExtra("game_cover").toString()
        val title = intent.getStringExtra("game_title").toString()
        bundle.putString("game_cover", imagePath) // put image data in Intent
        bundle.putString("game_title", title) // put image data in Intent

        if (savedInstanceState == null) {
            Utilities.insertFragment(
                this,
                GameDetFragment(),
                GameDetFragment::class.java.simpleName, bundle
            )
        }


        /*Utilities.insertFragment(
            this,
            GameDetFragment(),
            GameDetFragment::class.java.simpleName,
            bundle
        )*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_det_top_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.app_bar_settings) {
            val intent = Intent(this, R.layout.activity_drawer_main.javaClass)
            this.startActivity(intent)
            return true
        }
        return false
    }
}