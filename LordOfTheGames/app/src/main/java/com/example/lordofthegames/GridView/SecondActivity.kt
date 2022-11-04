package com.example.lordofthegames.GridView

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lordofthegames.R

class SecondActivity: AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        selectedImage = findViewById(R.id.selectedImage)
        val intent: Intent = intent
        val imagePath = intent.getIntExtra("copertina", 0).
        val ima = intent.getIntArrayExtra("copertina")
        /*if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            icon.setImageDrawable(drawable)
        }
        * */

        println("BESUGHI 2   " + imagePath)

        println("BESUGHI 3   " + ima)
        //selectedImage.setImageResource(intent.getIntExtra("ic_launcher_foreground", 0))
        selectedImage.setImageResource(intent.getIntExtra("copertina", 0))
    }
}