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
    //      creare il framelayout per visualizzare la singola notifica con tutto quello che ha scritto e il bottone del calendario se esiste una data
    //      sistemare l'update della view

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


        notificationAdapter = NotificationAdapter(this, list, requireActivity())

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.recyclerViewNotification.adapter = notificationAdapter

        //TODO: dare vita ai bottoni trash e calendar e vedere se si può fare qualcosa per il colore
        bind.trashBtn.setOnClickListener {
            bind.notificationSpecificFrameLayout.visibility = View.GONE
        }

        bind.addToCalendarBtn.setOnClickListener {
            bind.notificationSpecificFrameLayout.visibility = View.GONE
        }

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
        Log.e("ALLORA?", "${list[position]}")
        readSingleNotification(user_nick, list[position].id)
        val item = list[position]
        bind.notificationTitleSpecific.text = item.title
        bind.notificationContentSpecific.text = item.content

        bind.notificationSpecificFrameLayout.visibility = View.VISIBLE
        bind.notificationDateSpecific.text =
            if(item.data_inizio != null && item.data_fine == null)
                item.data_inizio
            else if(item.data_inizio != null && item.data_fine != null)
                "${item.data_inizio} ~ ${item.data_fine}"
            else ""

        this.updateView()
    }

    fun updateView(){
        notificationAdapter.updateView(viewm.getNotification(user_nick))
    }
}