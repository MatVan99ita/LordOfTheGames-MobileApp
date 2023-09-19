package com.example.lordofthegames.user_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
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

            //requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putString("logged", "").apply()
            val intent = Intent(context, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
            //login(password.text.toString(), mail.text.toString());
        }


        super.onViewCreated(view, savedInstanceState)
    }

    fun login(passw: String, email: String) {

        val usr = repository.getUser(email, passw)
        if (usr != null){
            val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("logged", "")
            editor.putString("mail", email)
            editor.putString("nick", usr)
            editor.apply()
            parentFragmentManager.beginTransaction().replace(R.id.login_fragment, LoggedInFragment()).addToBackStack(null).commit()
            //val myValue = sharedPrefs.getString("myKey", defaultValue)
            //Si passa alla logged interface e si tiene traccia di una variabile logged da usare per le varie cose

            //val exists = sharedPrefs.contains("logged") <- da usare in caso

            /*Per rimuovere la cosa in modo da poter checckare meglio
            * editor.remove("logged")
                editor.apply()
            * */
        }
        //TODO("Prendere i dati e cofrontarli")

    }
}