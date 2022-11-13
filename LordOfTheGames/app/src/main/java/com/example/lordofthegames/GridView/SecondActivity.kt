package com.example.lordofthegames.GridView

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lordofthegames.R
import android.net.Uri
import android.widget.TextView

class SecondActivity: AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    private lateinit var gtitle: TextView
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        selectedImage = findViewById(R.id.selectedImage)
        gtitle = findViewById(R.id.game_title)
        val intent: Intent = intent
        val imagePath = intent.getStringExtra("game_cover").toString()
        val title = intent.getStringExtra("game_title").toString()
        //val ima = intent.getIntArrayExtra("copertina")
        if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(this, this.resources.getIdentifier(imagePath, "drawable", this.packageName))
            selectedImage.setImageDrawable(drawable)
        } else if(imagePath.contains("gabibbo")) {
            val drawable: Drawable? = ContextCompat.getDrawable(this, this.resources.getIdentifier("ic_gabibbo_test", "mipmap", this.packageName))
            selectedImage.setImageDrawable(drawable)
        }
        gtitle.text = title

        Log.i("BESUGHIo" , imagePath)

        //Log.i("BESUGHI 3   " , ima.toString())
        //selectedImage.setImageResource(intent.getIntExtra("ic_launcher_foreground", 0))
        //selectedImage.setImageResource(imagePath)
    }
}