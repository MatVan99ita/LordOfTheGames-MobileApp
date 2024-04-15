package com.example.lordofthegames.Community

import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.databinding.FragmentCommunitySpecificBinding
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.DiscussionItem
import com.example.lordofthegames.recyclerView.DiscussionSpecificAdapter
import com.example.lordofthegames.recyclerView.OnItemListener

class CommunitySpecificFragment: Fragment(), OnItemListener {

    private lateinit var adapter: DiscussionSpecificAdapter
    private lateinit var viewm: DiscussionViewModel
    private lateinit var list: List<DiscussionItem>
    private lateinit var bind: FragmentCommunitySpecificBinding
    private var list2: List<DiscussionItem> = listOf(
        DiscussionItem(
            Discussion(666, "pipo", "ritto", 666), 69, 420
        ),
        DiscussionItem(
            Discussion(666, "pipo", "ritto", 666), 69, 420
        ),
        DiscussionItem(
            Discussion(666, "pipo", "ritto", 666), 69, 420
        ),
        DiscussionItem(
            Discussion(666, "pipo", "ritto", 666), 69, 420
        ),
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        bind = FragmentCommunitySpecificBinding.inflate(layoutInflater, container, false);
        list = viewm.selectAllDiscussion(requireActivity().intent.getStringExtra("game_title").toString())

        list.forEach { el -> Log.i("OnCreateViewDSF", el.toString()) }

        adapter = DiscussionSpecificAdapter(requireActivity(), this, list,)

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.recyclerViewDiscussion.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {

        val act: Activity? = activity

    }



}