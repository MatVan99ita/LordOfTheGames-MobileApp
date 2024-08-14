package com.example.lordofthegames.Community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.ActivityCommunityBinding
import com.example.lordofthegames.user_login.LoggedActivity
import com.google.android.material.navigation.NavigationView

class CommunityActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var discussion_intent: Intent
    private lateinit var string: String
    private lateinit var navigationView: NavigationView
    private var game_ref: Int = -1
    private lateinit var bind: ActivityCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(bind.root)
        drawerLayout = bind.communityActivityDrawer
        discussion_intent = intent
        val bundle = Bundle()
        val title = intent.getStringExtra("game_title").toString()
        string = title
        bundle.putString("game_title", title)

        //drawerLayout = findViewById(R.id.community_activity_drawer)


        Utilities.insertFragment(
            this,
            CommunitySpecificFragment(),
            CommunitySpecificFragment::class.java.simpleName,
            bundle,
        )

        Utilities.setUpToolBar(
            this,
            findViewById(R.id.topbar),
            title,
            bind.communityActivityDrawer,
            null,
        )
        navigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle = Utilities.setUpDrawer(
            bind.communityActivityDrawer,
            navigationView,
            this,
            null
        )

        val banana = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val communityViewModel: DiscussionViewModel = ViewModelProvider(this)[DiscussionViewModel::class.java]
        communityViewModel.getUsrPosition(banana.getString("email", "BANANA")!!)?.let {
            Utilities.setDrawerWithUser(
                this.findViewById<View>(R.id.nav_view) as NavigationView,
                banana.getString("nickname", "BANANA").toString(),
                banana.getString("email", "BANANA").toString(),
                communityViewModel.getUsrImg(banana.getString("email", "BANANA").toString()),
                it
            )
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_setting -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    this.startActivity(intent)
                }
                R.id.nav_usr -> {
                    val intent = Intent(this, LoggedActivity::class.java)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    this.startActivity(intent)
                }

            }
            true
        }
        Log.e("CreateOPTMenu", menu.toString())
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        val drawerLayout = bind.communityActivityDrawer
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}