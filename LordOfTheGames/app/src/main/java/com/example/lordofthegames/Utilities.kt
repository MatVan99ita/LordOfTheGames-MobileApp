package com.example.lordofthegames

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class Utilities {
    private final val REQUEST_IMAGE_CAPTURE: Int = 1

    fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String){

        var transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

        transaction.replace(androidx.fragment.R.id.fragment_container_view_tag, fragment, tag)

        if(!(fragment is MainActivity)){
            transaction.addToBackStack(tag)
        }

        transaction.commit()
    }
}