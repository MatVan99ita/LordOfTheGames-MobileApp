package com.example.lordofthegames.home.mygame

import android.os.Bundle
import androidx.fragment.app.Fragment

class PlanToPlayFragment: Fragment() {
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlanToPlayFragment {
            return PlanToPlayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}