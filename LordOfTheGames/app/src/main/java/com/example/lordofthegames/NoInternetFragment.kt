package com.example.lordofthegames

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Community.CommunityActivity
import com.example.lordofthegames.databinding.FragmentCommunityListBinding
import com.example.lordofthegames.databinding.FragmentNoInternetBinding
import com.example.lordofthegames.home.CommunityViewModel
import com.example.lordofthegames.recyclerView.CommunitiesAdapter
import com.example.lordofthegames.recyclerView.CommunityItem

class NoInternetFragment: Fragment() {

    private lateinit var bind: FragmentNoInternetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentNoInternetBinding.inflate(layoutInflater, container, false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}