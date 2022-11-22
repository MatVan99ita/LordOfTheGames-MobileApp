package com.example.lordofthegames

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


public class Utilities {
    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1
        public fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String){
            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.gameGridView, fragment, tag)

            when(fragment){
                is HomeFragment -> transaction.addToBackStack(tag)
            }

            transaction.commit()
        }


        /**
         * Utility to convert a drawable into a bitmap (to store the android icon as a bitmap)
         * @param drawable the drawable of the android icon
         * @return the bitmap of the drawable
         */
        public fun drawableToBitmap(drawable: Drawable): Bitmap? {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        fun setUpToolBar(activity: AppCompatActivity, title: String?) {
            val actionBar: ActionBar? = activity.supportActionBar
            if (actionBar == null) {
                val toolBar = Toolbar(activity)
                activity.setSupportActionBar(toolBar)
            } else {
                activity.supportActionBar!!.title = title
            }
        }
    }



}