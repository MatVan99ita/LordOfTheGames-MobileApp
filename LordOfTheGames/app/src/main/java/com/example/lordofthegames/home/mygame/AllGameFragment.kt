package com.example.lordofthegames.home.mygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations

class AllGameFragment(): Fragment() {
    private val _index = MutableLiveData<Int>()
    //val text: LiveData<String> = Transformations.map(_index) {
    //    "Hello world from section: $it"
    //}

    fun setIndex(index: Int) {
        _index.value = index
    }
    private val ARG_SECTION_NUMBER = "1"

    fun newInstance(i: Int): Fragment {
        return CompletedView().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, i)
            }
        }
    }
}