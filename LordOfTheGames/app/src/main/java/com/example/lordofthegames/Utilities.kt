package com.example.lordofthegames

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room.databaseBuilder
import com.example.lordofthegames.Database.LOTGDAO
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.Flow


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
            drawerLayout: DrawerLayout?,
            menu: Int?,
        ) {

            if (menu != null) {
                toolbar.inflateMenu(menu)
            }

            toolbar.title = title

            if(drawerLayout != null) {
                toolbar.setNavigationOnClickListener {
                    val actionBarDrawerToggle = ActionBarDrawerToggle(
                        activity,
                        drawerLayout,
                        R.string.belandih,
                        R.string.besughi
                    )
                    actionBarDrawerToggle.isDrawerIndicatorEnabled = false
                    toolbar.setNavigationIcon(R.drawable.ic_t_pose)
                    drawerLayout.addDrawerListener(actionBarDrawerToggle)
                    actionBarDrawerToggle.syncState()
                    //drawerLayout.closeDrawers()
                }
            }
            activity.setSupportActionBar(toolbar)

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

        fun createDatabase(context: Context) {/*
            val lotgViewModel: LOTGViewModel = ViewModelProvider(owner)[LOTGViewModel::class.java]

            val LOTGViewModel by viewModels<LOTGViewModel> {
                LOTGViewModelFactory(repository = (application as GameApplication).repository)
            }
            lotgViewModel.addItem(Game(1, "banana", "", ""))
            // */
            /** TODO Aggiungere questo tipo di cosa ad Utilities
            //lotgViewModel.addGame(Game(1, "banana", "", "")) <- fare in modo che quando parte prende e genera direttamente il database dal file "*.db" in modo da non doverlo scrivere qui
             */


            // Start the Room database

            // Start the Room database
            val db: LOTGDatabase = databaseBuilder(
                context,
                LOTGDatabase::class.java, "lotgdatabase"
            ).build()

            // Create the DAO and repository

            // Create the DAO and repository
            val myDao: LOTGDAO = db.lotgdao()
            val myRepository = LOTGRepository(myDao)

            // Use the repository to interact with the database

            // Use the repository to interact with the database
            val myEntities: Flow<List<Game>> = myRepository.getAllGame()
            print(myEntities.toString())
        }

        /*fun fillRoomDatabase(context: Context, vararg databaseNames: String) {
            for (databaseName in databaseNames) {
                val dbFile = File(context.getDatabasePath(databaseName).path)
                val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
                val query = "SELECT * FROM game"
                val cursor = db.rawQuery(query, null)
                val list = mutableListOf<Game>()
                while (cursor.moveToNext()) {
                    val data = Game(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("img")),
                        cursor.getLong(cursor.getColumnIndex("status"))
                    )
                    list.add(data)
                }
                cursor.close()
                db.close()
                val roomDb = Room.databaseBuilder(context, LOTGDatabase::class.java, "my-db-name").build()
                roomDb.lotgdao().fillGame(list)
                roomDb.close()
            }
        } // */
    }

}