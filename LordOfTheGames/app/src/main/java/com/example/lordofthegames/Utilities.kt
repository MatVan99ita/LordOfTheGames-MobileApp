package com.example.lordofthegames

import android.content.Intent
import android.view.View
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.gridView.CustomAdapter
import com.example.lordofthegames.gridView.SecondActivity

public class Utilities {
    public fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String){
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

        transaction.replace(R.id.gameGridView, fragment, tag)

        when(fragment){
            is HomeFragment -> transaction.addToBackStack(tag)
        }

        transaction.commit()
    }


}