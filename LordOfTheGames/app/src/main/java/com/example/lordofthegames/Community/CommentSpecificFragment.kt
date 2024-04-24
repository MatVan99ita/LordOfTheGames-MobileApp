package com.example.lordofthegames.Community

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.databinding.FragmentCommunitySpecificBinding
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.recyclerView.CommentCardAdapter
import com.example.lordofthegames.recyclerView.CommentItem
import com.example.lordofthegames.recyclerView.DiscussionItem
import com.example.lordofthegames.recyclerView.DiscussionSpecificAdapter
import com.example.lordofthegames.recyclerView.OnItemListener

class CommentSpecificFragment : Fragment(), OnItemListener {

    private lateinit var adapter: CommentCardAdapter
    private lateinit var viewm: DiscussionViewModel
    private lateinit var list: List<Comments>
    private lateinit var bind: FragmentCommunitySpecificBinding




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        bind = FragmentCommunitySpecificBinding.inflate(layoutInflater, container, false);
        list = viewm.getDiscussionComments(requireActivity().intent.getIntExtra("discussion_id", -1))

        list.forEach { el -> Log.i("OnCreateViewDSF", el.toString()) }

        adapter = CommentCardAdapter(this, list, "", requireActivity())

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.recyclerViewDiscussion.adapter = adapter
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