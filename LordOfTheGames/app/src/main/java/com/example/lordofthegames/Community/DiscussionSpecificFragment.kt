package com.example.lordofthegames.Community

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentDiscussionContentSpecificBinding
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.CommentListAdapater
import com.example.lordofthegames.recyclerView.OnItemListener


class DiscussionSpecificFragment : Fragment(), OnItemListener{

    private lateinit var binding: FragmentDiscussionContentSpecificBinding
    private lateinit var viewm: DiscussionViewModel
    private lateinit var disccussion: Pair<Discussion, List<Comments>>
    private lateinit var adapter: CommentListAdapater
    private var discussion_id: Int = 0
    private lateinit var this_usr: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        binding = FragmentDiscussionContentSpecificBinding.inflate(layoutInflater)
        val bundle: Bundle? = arguments

        val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        this.this_usr = banana.getString("nickname", "Besugo")!!

        if(bundle != null){
            discussion_id = bundle.getInt("discussion_id")
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
        binding.etAddComment.background.alpha = 255
        binding.recyclerViewComments.adapter = adapter

        binding.etAddComment.setText("")


        binding.btnSendComment.setOnClickListener {
            if(binding.etAddComment.text?.length!! > 0){
                if(this.insertComment(binding.etAddComment.text.toString())>0){
                    this.updateView()
                }
            } else {
                Utilities.showaToast(requireContext(), "You serious?")
            }
        }

    }

    private fun insertComment(text: String): Long {
        var i: Long = viewm.insertComment(text, disccussion.first.discussion_id, this_usr)

        if(i > 0) i += viewm.sendNotificationToUser(
            userRef = disccussion.first.user_ref,
            usr = this_usr,
            partial_content =
            if(text.length < 20)
                text
            else
            text.substring(
                0,
                20
            )
        )
        return i
    }

    private fun updateView(){
        val l = viewm.getDiscussionComments(discussion_id)
        adapter.updateView(l)
        binding.etAddComment.text!!.clear()
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.recyclerViewComments.scrollToPosition(l.lastIndex)
        requireActivity().currentFocus?.let { view ->
            (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    override fun onItemClick(view: View, position: Int) {
    }

}