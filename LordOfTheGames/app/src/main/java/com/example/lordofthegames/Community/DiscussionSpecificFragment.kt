package com.example.lordofthegames.Community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.databinding.FragmentDiscussionContentSpecificBinding
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.DiscussionItem
import org.w3c.dom.Comment

class DiscussionSpecificFragment : Fragment(){

    private lateinit var binding: FragmentDiscussionContentSpecificBinding
    private lateinit var viewm: DiscussionViewModel
    private lateinit var disccussion: Map<Discussion, List<Comment>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        binding = FragmentDiscussionContentSpecificBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}