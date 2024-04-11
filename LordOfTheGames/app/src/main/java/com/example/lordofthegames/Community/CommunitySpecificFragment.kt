package com.example.lordofthegames.Community

import android.os.Bundle
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


    private lateinit var viewm: DiscussionViewModel
    private lateinit var bind: FragmentCommunitySpecificBinding
    private lateinit var recyler: RecyclerView
    private lateinit var list: List<DiscussionItem>
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
    private lateinit var adapter: DiscussionSpecificAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // val view = inflater.inflate(R.layout.fragment_community_specific, container, false)

        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        bind = FragmentCommunitySpecificBinding.inflate(layoutInflater, container, false);
        list = viewm.selectAllDiscussion(requireActivity().intent.getStringExtra("game_title").toString())
        adapter = DiscussionSpecificAdapter(requireActivity(), this, list2,)
        //recycler = view.findViewById(R.id.recycler_view_notification)

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recyler.adapter = adapter

        bind.recyclerViewDiscussion.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }



}