package com.example.lordofthegames.user_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.User
import com.google.android.material.textfield.TextInputEditText

class SignInFragment: Fragment() {

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var reqpassword: TextInputEditText
    private lateinit var lbl_error: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val login_btn: Button = view.findViewById(R.id.login_btn)
        val signin_btn: Button = view.findViewById(R.id.signin_btn)
        nick = requireView().findViewById(R.id.nickname_textinput)
        mail = requireView().findViewById(R.id.mail_textinput)
        password = requireView().findViewById(R.id.password_textinput)
        lbl_error = requireView().findViewById(R.id.txt_error)
        reqpassword = requireView().findViewById(R.id.confirm_password_textinput)

        login_btn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, LogInFragment()).commit()
        }

        signin_btn.setOnClickListener {
            Utilities.login(LoggedActivity(), "", "", "");
            //requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putString("logged", "").apply()
            val intent = Intent(context, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)




            //signin(nick.text.toString(), password.text.toString(), mail.text.toString());
            // TODO("Aggiungere l'if sul controllo della password")
        }
        // TODO("Aggiungere il controllo tra password e reqpassword tramite l'onchange")

        super.onViewCreated(view, savedInstanceState)
    }

    fun addUser(user: User) {
        //repository.insertUser(user)
    }

    fun isValidPassword(password: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
        return password.matches(regex)
    }

    fun isValidMail(email: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matches(email)
    }

    fun signin(nickn: String, passw: String, c_passw: String, email: String) {


        if(!isValidMail(email)){
            lbl_error.error = "Mail is required. Must be name@domain.net"
            lbl_error.requestFocus()
        } else if(nickn.length < 6){
            lbl_error.error = "Nickname must be 6(or more) character ";
            lbl_error.requestFocus();
            return;
        }

        if(!isValidPassword(passw)) {
            lbl_error.error = "Password is required. Must be 6 character. Must have a special character, a number and a Uppercase chapter(Ex.:Banana33!)";
            lbl_error.requestFocus();
            return;
        } else if(c_passw != passw){
            lbl_error.error = "Password must be the same";
            lbl_error.requestFocus();
        }
        parentFragmentManager.beginTransaction().replace(R.id.signin_fragment, LoggedInFragment()).addToBackStack(null).commit()

        Log.w("SIGNIN", "$nickn $email $passw")
        /**
         *
         *     TODO: aggiungere i dati al db e il log nelle shared pref
         *              val salt = Utilities.generateSalt()
         *              val hashedPassword = Utilities.hashPassword(passw, salt)
         *              val check = 10// repository.insertUser(User(email, nickn, passw))
         *              if(check > 0){
         *                  val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
         *                  val editor = sharedPrefs.edit()
         *                  editor.putString("logged", "")
         *                  editor.putString("mail", email)
         *                  editor.putString("nick", nickn)
         *                  editor.apply()
         *                  //parentFragmentManager.beginTransaction().replace(R.id.login_fragment, LoggedInFragment()).addToBackStack(null).commit()
         *              }
         */

    }
}