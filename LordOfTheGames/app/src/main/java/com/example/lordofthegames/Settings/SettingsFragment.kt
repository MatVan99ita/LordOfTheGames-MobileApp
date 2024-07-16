package com.example.lordofthegames.Settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources.Theme
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.lordofthegames.R
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputLayout
import java.lang.Thread.sleep
import java.lang.reflect.Field


class SettingsFragment: Fragment() {


    private var isNightModeOn = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

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


            val switchThemeButton: SwitchCompat = view.findViewById(R.id.material_switch_theme)
            val textInputLayout: TextInputLayout = view.findViewById(R.id.username_textinput)
            val editText: EditText? = textInputLayout.editText

            val textView: TextView = view.findViewById(R.id.username_textview)

            val sharedPreferences: SharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

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

            if(sharedPreferences.getString("Theme", "NoTheme").equals("Night")) { switchThemeButton.isChecked = true }

            val editor = sharedPreferences.edit()
            /***
             * TODO:
             *      rifare la scelta del tema o scegliere giusto 2 temi
             */
            switchThemeButton.setOnCheckedChangeListener { buttonView, isChecked ->
                // Post the check to the main thread to ensure that the switch's state is updated
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    if (isChecked) {
                        // setting theme to night mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        buttonView.text = "Night Mode"
                        editor.putString("Theme", "Night")
                        editor.apply()
                    } else {
                        // setting theme to light theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        editor.putString("Theme", "Light")
                        buttonView.text = "Light Mode"
                        editor.apply()
                    }
                }
            }


        }//endif





    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onCreateOptionsMenu(menu, inflater)",
        "androidx.fragment.app.Fragment"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    fun getThemeName(context: Context, theme: Theme): String {
        return try {
            val mThemeResId: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val fThemeImpl: Field = theme.javaClass.getDeclaredField("mThemeImpl")
                if (!fThemeImpl.isAccessible) fThemeImpl.isAccessible = true
                val mThemeImpl: Any = fThemeImpl.get(theme)
                val fThemeResId: Field = mThemeImpl.javaClass.getDeclaredField("mThemeResId")
                if (!fThemeResId.isAccessible) fThemeResId.isAccessible = true
                fThemeResId.getInt(mThemeImpl)
            } else {
                val fThemeResId: Field = theme.javaClass.getDeclaredField("mThemeResId")
                if (!fThemeResId.isAccessible) fThemeResId.isAccessible = true
                fThemeResId.getInt(theme)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                theme.resources.getResourceEntryName(mThemeResId)
            } else context.resources.getResourceEntryName(mThemeResId)
        } catch (e: Exception) {
            // Theme returned by application#getTheme() is always Theme.DeviceDefault
            "porcaddio"
        }
    }

}
