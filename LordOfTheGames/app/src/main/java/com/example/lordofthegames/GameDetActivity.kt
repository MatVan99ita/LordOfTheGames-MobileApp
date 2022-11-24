package com.example.lordofthegames

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class GameDetActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent = intent
        setContentView(R.layout.activity_gamedet)
    }
}