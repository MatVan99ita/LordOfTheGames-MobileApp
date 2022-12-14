package com.example.lordofthegames

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.home.HomeFragment
import com.google.android.material.navigation.NavigationView


class Utilities {
    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1

        fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String, bundle: Bundle?){


            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container_view, fragment, tag)

            if (fragment !is HomeFragment && fragment !is GameDetFragment){
                transaction.addToBackStack(tag);
            }

            transaction.commit()
        }

        fun setUpDrawer(
            drawerLayout: DrawerLayout,
            navigationView: NavigationView,
            activity: AppCompatActivity,
            settingActivity: String? = null
        ): ActionBarDrawerToggle {

            val actionBarDrawerToggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.belandih, R.string.besughi)
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            drawerLayout.closeDrawers()

            if(settingActivity != null){
                navigationView.menu.findItem(R.id.nav_setting).isVisible = false
            }


            return actionBarDrawerToggle
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

        fun setUpToolBar(
            activity: AppCompatActivity,
            toolbar: Toolbar,
            title: String?,
            drawerLayout: DrawerLayout,
            menu: Int?,
        ) {

            if (menu != null) {
                toolbar.inflateMenu(menu)
            }

            toolbar.title = title

            toolbar.setNavigationOnClickListener {
                val actionBarDrawerToggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.belandih, R.string.besughi)
                drawerLayout.addDrawerListener(actionBarDrawerToggle)
                actionBarDrawerToggle.syncState()
                drawerLayout.closeDrawers()
            }

            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);


            /*var actionBar: ActionBar? = activity.supportActionBar
            //val navController: NavController = NavController(context = activity)
            //val appBarConfiguration = AppBarConfiguration(navController.graph)
            if (actionBar == null) {
                val toolBar = Toolbar(activity)
                activity.setSupportActionBar(toolBar)
                actionBar = activity.supportActionBar
            }
            actionBar?.title = title
            //actionBar?.displayOptions = DISPLAY_USE_LOGO
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24dp)
            actionBar?.setDisplayHomeAsUpEnabled(true)
            //supportActionBar?.setDisplayHomeAsUpEnabled(true)1
            //toolBar.setNavigationIcon(R.drawable.ic_menu_24dp)

            Log.e("UTIL SETUP", actionBar.toString())*/
        }

        fun setUpBottomNavigation(){

        }



    }

}