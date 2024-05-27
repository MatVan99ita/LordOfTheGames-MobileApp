package com.example.lordofthegames.user_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class LogInFragment: Fragment() {

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login_view: View
    private lateinit var loggedViewModel: LoggedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loggedViewModel = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val login_btn: Button = view.findViewById(R.id.login_btn)
        val signin_btn: Button = view.findViewById(R.id.signin_btn)
        mail = requireView().findViewById(R.id.mail_textinput)
        password = requireView().findViewById(R.id.password_textinput)

        signin_btn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, SignInFragment()).commit()
        }

        login_btn.setOnClickListener {//TODO: ANDARE AFFANCULO NELL'ACTIVITY PRICIPALE
            this.login(password.text.toString(), mail.text.toString())
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun login(passw: String, email: String) {

        if(passw != "" && email != "") {
            loggedViewModel.getUsrByMailPass(email, passw).observe(viewLifecycleOwner) {users ->
                users?.forEach{ user ->
                    if (user != null) {
                        requireActivity()
                            .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            .edit()
                            .putString("email", user.mail)
                            .putString("nickname", user.nickname)
                            .putString("logged", "SI")
                            .apply()

                        val intent = Intent(context, MainActivity::class.java)
                        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        this.startActivity(intent)

                    } else {
                        Utilities.showaToast(requireContext(), "Wrong mail or password")
                    }
                }
            }
        }
    }
}