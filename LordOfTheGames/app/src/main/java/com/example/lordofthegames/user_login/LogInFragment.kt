package com.example.lordofthegames.user_login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.User
import com.google.android.material.textfield.TextInputEditText

class LogInFragment: Fragment() {
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
        login_view = inflater.inflate(R.layout.fragment_login, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val login_btn: Button = view.findViewById(R.id.login_btn)
        val signin_btn: Button = view.findViewById(R.id.signin_btn)
        mail = requireView().findViewById(R.id.mail_textinput)
        password = requireView().findViewById(R.id.confirm_password_textinput)

        signin_btn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.login_fragment, SignInFragment()).addToBackStack(null).commit()
        }

        login_btn.setOnClickListener {
            login(password.text.toString(), mail.text.toString());
        }


        super.onViewCreated(view, savedInstanceState)
    }

    fun login(passw: String, email: String) {

        TODO("Prendere i dati e cofrontarli")

    }
}