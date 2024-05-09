package com.example.lordofthegames.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.databinding.FragmentNotificationBinding
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.recyclerView.NotificationAdapter
import com.example.lordofthegames.recyclerView.OnItemListener


class NotificationFragment: Fragment(), OnItemListener {

    private lateinit var list: List<Notification>
    private lateinit var viewm: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var bind: FragmentNotificationBinding

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //val view = inflater.inflate(R.layout.fragment_notification, container, false)
        viewm = ViewModelProvider(requireActivity())[NotificationViewModel::class.java]
        bind = FragmentNotificationBinding.inflate(layoutInflater, container, false);
        list = viewm.getNotification()
        notificationAdapter = NotificationAdapter(this, list, requireActivity())
        //recycler = view.findViewById(R.id.recycler_view_notification)

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.recyclerViewNotification.adapter = notificationAdapter
    }

    override fun onItemClick(view: View, position: Int) {
    }
}