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

        /*
        * if (imagePath.contains("ic_")){
            val drawable: Drawable? = ContextCompat.getDrawable(activity, activity.resources.getIdentifier(imagePath, "drawable", activity.packageName))
            icon.setImageDrawable(drawable)
        }
        * */
        //selectedImage.setImageResource(intent.getIntExtra("ic_launcher_foreground", 0))
        selectedImage.setImageResource(intent.getIntExtra("copertina", 0))
    }
}