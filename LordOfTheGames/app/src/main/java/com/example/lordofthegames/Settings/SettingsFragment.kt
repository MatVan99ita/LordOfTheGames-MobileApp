package com.example.lordofthegames.Settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.google.android.material.textfield.TextInputLayout

class SettingsFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity? = activity

        if(activity != null){
            val textInputLayout: TextInputLayout = view.findViewById(R.id.username_textinput)
            val editText: EditText? = textInputLayout.editText

            val textView: TextView = view.findViewById(R.id.username_textview)

            val sharedPreferences: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)

            textView.text = sharedPreferences.getString("Settings", "username")

            editText!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

                override fun afterTextChanged(editable: Editable) {
                    textView.text = editable
                    val editor = sharedPreferences.edit()
                    editor.putString("Settings", editable.toString())
                    editor.apply()
                }
            })
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onCreateOptionsMenu(menu, inflater)",
        "androidx.fragment.app.Fragment"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }



}