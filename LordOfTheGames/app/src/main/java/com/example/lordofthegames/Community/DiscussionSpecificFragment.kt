package com.example.lordofthegames.Community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.databinding.FragmentDiscussionContentSpecificBinding
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.CommentListAdapater
import com.example.lordofthegames.recyclerView.DiscussionSpecificAdapter
import com.example.lordofthegames.recyclerView.OnItemListener

class DiscussionSpecificFragment : Fragment(), OnItemListener{

    private lateinit var binding: FragmentDiscussionContentSpecificBinding
    private lateinit var viewm: DiscussionViewModel
    private lateinit var disccussion: Pair<Discussion, List<Comments>>
    private lateinit var adapter: CommentListAdapater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        binding = FragmentDiscussionContentSpecificBinding.inflate(layoutInflater)
        val bundle: Bundle? = arguments
        if(bundle != null){
            val discussion_id = bundle.getInt("discussion_id")
            disccussion = Pair(
                    viewm.getDiscussionSpecific(discussion_id),
                    viewm.getDiscussionComments(discussion_id),
                )

            adapter = CommentListAdapater(requireActivity(), this, disccussion.second)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.discussionSpecificTitle.text = disccussion.first.title
        binding.discussionSpecificContent.text = disccussion.first.content
        binding.discussionSpecificUser.text = disccussion.first.user_ref

        binding.recyclerViewComments.adapter = adapter

    }

    override fun onItemClick(view: View, position: Int) {
    }

}