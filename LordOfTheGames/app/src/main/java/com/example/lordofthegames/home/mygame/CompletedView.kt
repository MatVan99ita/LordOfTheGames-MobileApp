package com.example.lordofthegames.home.mygame

import android.os.Bundle
import androidx.fragment.app.Fragment

class CompletedView: Fragment() {
    private val ARG_SECTION_NUMBER = "2"

    fun newInstance(sectionNumber: Int): Fragment {
        return CompletedView().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, sectionNumber)
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

    }
}