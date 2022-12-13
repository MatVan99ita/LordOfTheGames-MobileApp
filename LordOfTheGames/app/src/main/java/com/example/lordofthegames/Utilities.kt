package com.example.lordofthegames

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.home.HomeFragment
import com.google.android.material.navigation.NavigationView


public class Utilities {
    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1

        fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String, bundle: Bundle?, drawerLayout: DrawerLayout, navigationView: NavigationView, string: String): ActionBarDrawerToggle {


            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            fragment.arguments = bundle
            val actionBarDrawerToggle = this.setUpDrawer(drawerLayout, navigationView, activity)
            transaction.replace(R.id.fragment_container_view, fragment, tag)

            if (fragment !is HomeFragment && fragment !is GameDetFragment){
                transaction.addToBackStack(tag);
            }

            transaction.commit()
            return actionBarDrawerToggle
        }

        fun setUpDrawer(
            drawerLayout: DrawerLayout,
            navigationView: NavigationView,
            activity: AppCompatActivity,
        ): ActionBarDrawerToggle {
            val actionBarDrawerToggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.belandih, R.string.besughi)
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            drawerLayout.closeDrawers()
            return actionBarDrawerToggle
        }

        fun showSideMenu(activity: AppCompatActivity, fragment: Fragment){
            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            transaction.add(fragment, "side")
            transaction.show(fragment)

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

        fun setToolBarLayout(activity: AppCompatActivity, toolbar: Toolbar, string: String, view: View){
            activity.setSupportActionBar(toolbar)
            toolbar.title = string




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



    }

}