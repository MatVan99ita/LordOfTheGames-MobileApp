package com.example.lordofthegames.home.mygame

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lordofthegames.R
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    private val TAB_TITLES = arrayOf(
        R.string.all,
        R.string.playing,
        R.string.completed,
        R.string.dropped,
        R.string.plan_to_play,
    )
    fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return MyGameListFragment.newInstance(position + 1)
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {

        return if(position % 2 == 0) {
            Log.e("FragmentStateAdapter", "All")
            AllGameView()
        } //AllGameView().newInstance(position + 1)
        else {
            Log.e("FragmentStateAdapter", "Completed")
            CompletedView()
        } //CompletedView().newInstance(position + 1)
        //return MyGameListFragment()
    }
}