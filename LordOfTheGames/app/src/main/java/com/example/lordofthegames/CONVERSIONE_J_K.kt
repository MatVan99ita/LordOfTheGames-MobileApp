package com.example.lordofthegames

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.lordofthegames.recyclerView.CardItem


class CONVERSIONE_J_K {

    /*fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity = getActivity()
        if (activity != null) {
            Utilities.setUpToolBar(activity as AppCompatActivity, getString(R.string.settings))
            val textInputLayout: TextInputLayout = view.findViewById(R.id.username_textinput)
            val editText = textInputLayout.editText
            val textView: TextView = view.findViewById(R.id.username_textview)
            val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
            textView.text = sharedPreferences.getString("Settings", "username")
            editText!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    textView.text = editable
                    val editor = sharedPreferences.edit()
                    editor.putString("Settings", editable.toString())
                    editor.apply()
                }
            })
        }
    }*

    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) Utilities.insertFragment(
            this, HomeFragment(),
            HomeFragment::class.java.simpleName
        )
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
    }// *

}

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val cardItems: LiveData<List<CardItem>>

    init {
        val repository = CardItemRepository(application)
        cardItems = repository.getCardItemList()
    }
} // */
}

