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

class SecondActivity: AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        selectedImage = findViewById(R.id.selectedImage)
        val intent: Intent = intent
        val imagePath = intent.getStringExtra("game_cover").toString()
        //val ima = intent.getIntArrayExtra("copertina")
        if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(this, this.resources.getIdentifier(imagePath, "drawable", this.packageName))
            selectedImage.setImageDrawable(drawable)
        } else {
            uri = Uri.parse(imagePath)
            selectedImage.setImageURI(uri)
        }

        Log.i("BESUGHIo" , imagePath)

        //Log.i("BESUGHI 3   " , ima.toString())
        //selectedImage.setImageResource(intent.getIntExtra("ic_launcher_foreground", 0))
        //selectedImage.setImageResource(imagePath)
    }
}