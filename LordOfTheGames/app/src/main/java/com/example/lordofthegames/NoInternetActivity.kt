package com.example.lordofthegames

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lordofthegames.databinding.ActivityNoInternetBinding

class NoInternetActivity: AppCompatActivity() {

    private lateinit var bind: ActivityNoInternetBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityNoInternetBinding.inflate(layoutInflater).getRoot());
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }



}