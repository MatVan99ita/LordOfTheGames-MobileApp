package com.example.lordofthegames.Community

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentDiscussionContentSpecificBinding
import com.example.lordofthegames.db_entities.Comments
import com.example.lordofthegames.db_entities.Discussion
import com.example.lordofthegames.recyclerView.CommentListAdapater
import com.example.lordofthegames.recyclerView.OnItemListener
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.TimeZone


class DiscussionSpecificFragment : Fragment(), OnItemListener{

    private lateinit var binding: FragmentDiscussionContentSpecificBinding
    private lateinit var viewm: DiscussionViewModel
    private lateinit var disccussion: Pair<Discussion, List<Comments>>
    private lateinit var adapter: CommentListAdapater
    private var discussion_id: Int = 0
    private lateinit var this_usr: String
    private val actual_date_format: String = "dd/mm/yyyy-HH:mm"
    private val dateTime_formatter: DateTimeFormatter = DateTimeFormat.forPattern(actual_date_format)

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

            adapter = CommentListAdapater(requireActivity(), this, disccussion.second, viewm)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.discussionSpecificTitle.text = disccussion.first.title
        var s = disccussion.first.content
        if(disccussion.first.data_inizio != null){
            binding.addToCalendarDiscussBtn.visibility = View.VISIBLE
            s += "\n\nData inizio: ${disccussion.first.data_inizio}"
        }
        if(disccussion.first.data_fine != null){
            s+= "\nData fine: ${disccussion.first.data_fine}"
        }
        if(disccussion.first.location != null){
            s+= "\nLuogo: ${disccussion.first.location}"
        }

        binding.discussionSpecificContent.movementMethod = LinkMovementMethod.getInstance()
        binding.discussionSpecificContent.text = s
        Linkify.addLinks(binding.discussionSpecificContent, Linkify.WEB_URLS)

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

        binding.addToCalendarDiscussBtn.setOnClickListener {
            disccussion.first.location?.let { it2 ->
                this.addEventToCalendar(
                    title = disccussion.first.title,
                    description = disccussion.first.content,
                    begin = DateTime.parse(disccussion.first.data_inizio, dateTime_formatter).millis,
                    end =
                        if(disccussion.first.data_fine == null)
                            DateTime.parse(disccussion.first.data_inizio, dateTime_formatter).millis
                        else
                            DateTime.parse(disccussion.first.data_fine, dateTime_formatter).millis,
                    position = it2,
                )
            }
        }

        if(disccussion.first.img != null) {
            binding.discussionSpecificImg.visibility = View.VISIBLE

            binding.discussionSpecificImg.setImageBitmap(
                Utilities.stringToBitmap(
                    disccussion.first.img!!
                )
            )
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
        Utilities.generaNotificaNonSalvabile(
            requireActivity(),
            title = "Nuovo commento ricevuto",
            content = "Hai ricevuto una risposta al tuo post: ${disccussion.first.title}",
            CHANNEL_ID = MainActivity::class.java.simpleName,
        )
        Utilities.showaToast(requireContext(), "MODALITA' NOTIFICHE A SE STESSI")
    }

    override fun onItemClick(view: View, position: Int) {

    }

    fun addEventToCalendar(title: String, description: String, begin: Long, end: Long, position: String) {
        val intent: Intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, position)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, description)
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("email", "sessp"))
        startActivity(intent)
    }

}