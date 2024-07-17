package com.example.lordofthegames.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentNotificationBinding
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.recyclerView.NotificationAdapter
import com.example.lordofthegames.recyclerView.OnItemListener


class NotificationFragment: Fragment(), OnItemListener {

    private lateinit var list: List<Notification>
    private lateinit var viewm: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var bind: FragmentNotificationBinding
    private lateinit var user_nick: String
    private lateinit var recycler: RecyclerView

    //TODO: mettere tipo un pallino con o senza numero dentro sopra la campanella per sehnalare il numero di notifiche presenti
    //      fare in modo che il fab usi la funzione di update delle notifiche

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
        user_nick = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("nickname", "MinaScondo")!!
        list = viewm.getNotification(user_nick)
        Log.w("LALLISTA", list.toString())
        notificationAdapter = NotificationAdapter(this, list, requireActivity())
        //recycler = view.findViewById(R.id.recycler_view_notification)

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.recyclerViewNotification.adapter = notificationAdapter

        bind.notificationFab.setOnClickListener {
            var i = 0
            list.forEach {
                viewm.readAllNotification(user_nick)
            }
            if(i > 0){
                this.updateView()
                Utilities.showaToast (
                    requireContext(),
                    "Tutte le notifiche lette"
                )
            }
        }

    }

    fun readSingleNotification(user_ref: String, notification_id: Int): Int {
        return viewm.notificationRead(user_ref, notification_id)
    }

    override fun onItemClick(view: View, position: Int) {
        readSingleNotification(user_nick, list[position].id)
        this.updateView()
    }

    fun updateView(){
        notificationAdapter.updateView(viewm.getNotification(user_nick))
    }
}