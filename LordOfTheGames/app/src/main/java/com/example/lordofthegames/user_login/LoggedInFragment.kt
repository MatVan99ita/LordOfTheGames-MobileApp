package com.example.lordofthegames.user_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.R
import com.google.android.material.textfield.TextInputEditText

class LoggedInFragment: Fragment(){
    private lateinit var repository: LOTGRepository

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login_view: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        login_view = inflater.inflate(R.layout.fragment_loggedin, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}