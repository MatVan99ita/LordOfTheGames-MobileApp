package com.example.lordofthegames.user_login

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.User
import com.google.android.material.textfield.TextInputEditText

class SignInFragment: Fragment(), OnClickListener {
    private lateinit var repository: LOTGRepository

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun addUser(user: User) {
        repository.insertUser(user)
    }


    override fun onClick(p0: View?) {

        nick = requireView().findViewById<TextInputEditText>(R.id.nickname_textinput)
        mail = requireView().findViewById<TextInputEditText>(R.id.mail_textinput)
        password = requireView().findViewById<TextInputEditText>(R.id.confirm_password_textinput)


        //addUser(User(id,...))

        signin();

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

    fun signin() {
        val n = nick.text.toString()
        val p = password.text.toString()
        val m = mail.text.toString()

        if(!isValidMail(m)){
            nick.error = "Mail is required. Must be name@domain.net";
            nick.requestFocus();
            return;
        } else if(m.length < 6){
            nick.error = "Nickname must be 6(or more) character ";
            nick.requestFocus();
            return;
        }

        if(!isValidPassword(p)) {
            nick.error =
                "Password is required. Must be 6 character. Must have a special character, a number and a Uppercase chapter(Ex.:Banana33!)";
            nick.requestFocus();
            return;
        }

        if(n == ""){
            nick.error = "Nickname is required.";
            nick.requestFocus();
            return;
        } else if(n.length < 6){
            nick.error = "Nickname must be 6(or more) character ";
            nick.requestFocus();
            return;
        }


        val salt = Utilities.generateSalt()
        val hashedPassword = Utilities.hashPassword(p, salt)
        repository.insertUser(User(m, n, hashedPassword))

        // Validate email and password
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError("Email is required.");
            mEmailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError("Password is required.");
            mPasswordEditText.requestFocus();
            return;
        }

        // Perform login
        // ...
    }
}

/*
* public class LoginFragment extends Fragment {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Find views
        mEmailEditText = view.findViewById(R.id.email_edittext);
        mPasswordEditText = view.findViewById(R.id.password_edittext);
        mLoginButton = view.findViewById(R.id.login_button);

        // Set click listener for login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void login() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        // Validate email and password
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError("Email is required.");
            mEmailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError("Password is required.");
            mPasswordEditText.requestFocus();
            return;
        }

        // Perform login
        // ...
    }
}
*
* */