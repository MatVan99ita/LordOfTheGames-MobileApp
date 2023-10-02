package com.example.lordofthegames.user_login

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.db_entities.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.ExecutorService


class SignInFragment: Fragment() {

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var reqpassword: TextInputEditText
    private lateinit var lbl_error: TextView
    private lateinit var loggedViewModel: LoggedViewModel

    private lateinit var imageCapture: ImageCapture

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var currentPhotoPath: String

    private lateinit var photoFile: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loggedViewModel = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //photoFile = FileProvider.getUriForFile(requireContext(), "com.example.yourapp.fileprovider", outputDirectory)

        fragmentContainerView = requireActivity().findViewById(R.id.fragment_container_view)

        //if(requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_container_view) == requireActivity().findViewById(R.id.signin_fragment))
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

        mail.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Qui puoi eseguire il tuo controllo quando il focus è perso
                if (!isValidMail(mail.text.toString())) {
                    // Esempio di controllo: se il campo è vuoto, mostra un messaggio di errore
                    mail.error = "email must be like example@domain.exm"
                }
            }
        }

        password.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Qui puoi eseguire il tuo controllo quando il focus è perso
                if (!isValidMail(mail.text.toString())) {
                    // Esempio di controllo: se il campo è vuoto, mostra un messaggio di errore
                    mail.error = "password must be composed of almost 6 char, a lowercase and a uppercase letter, a number and a special char"
                }
            }
        }

        reqpassword.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Qui puoi eseguire il tuo controllo quando il focus è perso
                if (!isValidMail(mail.text.toString())) {
                    // Esempio di controllo: se il campo è vuoto, mostra un messaggio di errore
                    mail.error = "Different confirmation password"
                }
            }
        }


        signin_btn.setOnClickListener {

            if(!isValidMail(mail.text.toString())){
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                .setTitle("Error email")
                .setMessage("email must be like example@domain.exm")
                .setPositiveButton(
                    "Ok"
                ) { _: DialogInterface?, _: Int ->
                }

                .show()
            } else if(!isValidPassword(password.text.toString())){
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                    .setTitle("Error password")
                    .setMessage("password must be composed of almost 6 char, a lowercase and a uppercase letter, a number and a special char")
                    .setPositiveButton(
                        "Ok"
                    ) { _: DialogInterface?, _: Int ->
                    }

                    .show()
            } else if(reqpassword.text.toString() != password.text.toString()){
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                    .setTitle("Error password")
                    .setMessage("Different confirmation password")
                    .setPositiveButton(
                        "Ok"
                    ) { _: DialogInterface?, _: Int ->
                    }
                    .show()
            } else {
                val bundle = Bundle()
                //bundle.putStringArrayList("signin_val", arrayListOf(nick.text.toString(), reqpassword.text.toString(), mail.text.toString()))
                bundle.putString("nick", nick.text.toString())
                bundle.putString("passw", reqpassword.text.toString())
                bundle.putString("mail", mail.text.toString())

                val fragment2: SignInFragment2 = SignInFragment2()
                fragment2.arguments = bundle


                //signin(mail.text.toString(), reqpassword.text.toString(), nick.text.toString())
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment2).commit()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isValidPassword(password: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
        return password.matches(regex)
    }

    private fun isValidMail(email: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matches(email)
    }

    private fun signin(nickn: String, c_passw: String, email: String) {
        if(loggedViewModel.getUsrByNick(nickn).value != null){
            if(loggedViewModel.getUsrByMailPass(email, c_passw).value != null){
                val bundle = Bundle()
                bundle.putStringArrayList("signin_value", arrayListOf(nickn, c_passw, email))

                val fragment2 = SignInFragment2()
                fragment2.arguments = bundle

                parentFragmentManager.beginTransaction().replace(R.id.signin_fragment, SignInFragment2()).addToBackStack(null).commit()

            } else {
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                    .setTitle("User already exists")
                    .setMessage("Please retry login")
                    .setPositiveButton(
                        "Ok"
                    ){ _: DialogInterface?, _: Int ->
                    }
                    .show()
            }
        } else {
            MaterialAlertDialogBuilder(
                requireContext()
            )
                .setTitle("Nickname already exists")
                .setPositiveButton(
                    "Ok"
                ){ _: DialogInterface?, _: Int ->
                }
                .show()
        }


    }

}