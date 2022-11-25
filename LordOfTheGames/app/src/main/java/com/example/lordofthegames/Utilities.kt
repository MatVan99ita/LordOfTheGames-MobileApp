package com.example.lordofthegames

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


public class Utilities {
    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1

        fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String, bundle: Bundle?){
            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            Log.e("UTIL", bundle.toString())

            fragment.arguments = bundle

            Log.e("UTIL 2", fragment.arguments.toString())

            transaction.replace(R.id.fragment_container_view, fragment, tag)

            if (fragment !is HomeFragment){
                transaction.addToBackStack(tag);
            }

            transaction.commit()
        }


        /**
         * Utility to convert a drawable into a bitmap (to store the android icon as a bitmap)
         * @param drawable the drawable of the android icon
         * @return the bitmap of the drawable
         */
        fun drawableToBitmap(drawable: Drawable): Bitmap? {
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
            Log.e("BELINDI Util.setUp", actionBar.toString())
            if (actionBar == null) {
                val toolBar = Toolbar(activity)
                activity.setSupportActionBar(toolBar)

                Log.e("BELINDI Util.setUp", toolBar.toString())
            } else {
                activity.supportActionBar!!.title = title
            }
        }
    }



}