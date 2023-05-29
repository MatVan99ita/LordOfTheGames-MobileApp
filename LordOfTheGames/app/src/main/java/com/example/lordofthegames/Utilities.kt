package com.example.lordofthegames

import android.app.UiModeManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lordofthegames.Database.LOTGDAO
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Settings.SettingsFragment
import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.home.HomeFragment
import com.example.lordofthegames.user_login.LoggedActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import java.security.SecureRandom


class Utilities {
    companion object{

        const val REQUEST_IMAGE_CAPTURE = 1

        fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String, bundle: Bundle?){


            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container_view, fragment, tag)

            if (fragment !is HomeFragment && fragment !is GameDetFragment && fragment !is SettingsFragment){
                transaction.addToBackStack(tag);
            }

            transaction.commit()
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

            val sharedPreferences: SharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


            if(sharedPreferences.contains("logged")){
                val header_mail: TextView? = drawerLayout.findViewById(R.id.mail_header)
                val header_nick: TextView? = drawerLayout.findViewById(R.id.nickname_header)
                if (header_mail != null) {
                    header_mail.text = sharedPreferences.getString("mail", "BELANDIH")
                }
                if (header_nick != null) {
                    header_nick.text = sharedPreferences.getString("nick", "BESUGHI")
                }
            }

            if(activity.javaClass == SettingsActivity::class.java){
                navigationView.menu.findItem(R.id.nav_setting).isVisible = false
            }else if(activity.javaClass == LoggedActivity::class.java){
                navigationView.menu.findItem(R.id.nav_usr).isVisible = false
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


            //val db: LOTGDatabase = LOTGDatabase.getDatabase(context)
            val database by lazy { LOTGDatabase.getDatabase(context) }
            val repository by lazy { LOTGRepository(database.lotgdao()) }

            val myDao: LOTGDAO = database.lotgdao()
            //val myRepository = LOTGRepository(myDao)
            val myEntities: Flow<List<Game>> = repository.getAllGame()



            Log.e("POCODIODROPLET", myEntities.toString())

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
        fun darkThemeSwitch(context: Context, sharedPreferences: SharedPreferences): Boolean{
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            val mode = uiModeManager.nightMode
            val isDarkTheme = sharedPreferences.getBoolean("theme", mode == UiModeManager.MODE_NIGHT_YES)
            sharedPreferences.edit().putBoolean("theme", mode == UiModeManager.MODE_NIGHT_YES).apply()
            return isDarkTheme
        }

        fun rainbowBG(): GradientDrawable{
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BL_TR, intArrayOf(
                    R.color.rosso,
                    R.color.arancione,
                    R.color.giallo,
                    R.color.verde1,
                    R.color.verde2,
                    R.color.verde3,
                    R.color.azzurro1,
                    R.color.azzurro2,
                    R.color.blu,
                    R.color.viola1,
                    R.color.viola2,
                    R.color.viola3,
                    )
            )
            gradientDrawable.gradientType = GradientDrawable.SWEEP_GRADIENT
            gradientDrawable.setGradientCenter(0.5f, 0.5f)
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            return gradientDrawable
        }



        fun generateSalt(): String{
            val random = SecureRandom()
            val salt = ByteArray(16) // 16 bytes = 128 bits
            random.nextBytes(salt)
            return salt.toString()
        }

        fun hashPassword(password: String, salt: ByteArray): String {
            val sha256 = MessageDigest.getInstance("SHA-256")
            val hashedSalt = sha256.digest(salt)
            return hashedSalt.toString()
        }


    }
}