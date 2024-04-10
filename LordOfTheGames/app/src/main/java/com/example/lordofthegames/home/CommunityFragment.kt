package com.example.lordofthegames.home

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
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.databinding.FragmentCommunityListBinding
import com.example.lordofthegames.recyclerView.CommunitiesAdapter
import com.example.lordofthegames.recyclerView.CommunityItem
import com.example.lordofthegames.recyclerView.OnItemListener

class CommunityFragment : Fragment(), OnItemListener{

    private lateinit var adapter: CommunitiesAdapter
    private lateinit var viewm: CommunityViewModel
    private lateinit var list: List<CommunityItem>
    private lateinit var bind: FragmentCommunityListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[CommunityViewModel::class.java]
        bind = FragmentCommunityListBinding.inflate(layoutInflater, container, false)
        list = viewm.selectAllCommunity()
        adapter = CommunitiesAdapter(requireActivity(), this, list)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.recyclerViewCommunities.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        val act: Activity? = activity
        Log.i("LABBANANA", list[position].toString())
        if(act != null) {
            val intent = Intent(context, CommunityActivity::class.java)
            intent.putExtra("game_title", list[position].GameTitle)
            this.startActivity(intent)
        }
    }


}