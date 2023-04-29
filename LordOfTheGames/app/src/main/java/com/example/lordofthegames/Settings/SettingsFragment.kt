package com.example.lordofthegames.Settings

import android.app.Activity
import android.app.UiModeManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
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

            val switchCompat = view.findViewById<SwitchCompat>(R.id.theme_swap)

            val uiModeManager = requireActivity().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            val mode = uiModeManager.nightMode
            val isDarkTheme = sharedPreferences.getBoolean("theme", mode == UiModeManager.MODE_NIGHT_YES)
            switchCompat.isChecked = !isDarkTheme
            switchCompat.setOnCheckedChangeListener { _, isChecked ->
                sharedPreferences.edit().putBoolean("theme", !isDarkTheme).apply()
                AppCompatDelegate.setDefaultNightMode( if(isDarkTheme) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES);
            }



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

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper: Context = ContextThemeWrapper(requireContext(), R.style.Theme_LordOfTheGames_AZURE)
        return inflater.cloneInContext(contextThemeWrapper)
    }




}
