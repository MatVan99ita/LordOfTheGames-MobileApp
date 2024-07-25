package com.example.lordofthegames.home

import android.R.attr
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentNotificationBinding
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.recyclerView.NotificationAdapter
import com.example.lordofthegames.recyclerView.OnItemListener
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import java.util.TimeZone
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class NotificationFragment: Fragment(), OnItemListener {

    private lateinit var list: List<Notification>
    private lateinit var viewm: NotificationViewModel
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var bind: FragmentNotificationBinding
    private lateinit var user_nick: String
    private lateinit var recycler: RecyclerView
    private lateinit var selected_notification: Notification
    private lateinit var exeggutor: ExecutorService
    private val actual_date_format: String = "dd/mm/yyyy - HH:mm"
    private val dateTime_formatter: DateTimeFormatter = DateTimeFormat.forPattern(actual_date_format)

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


        exeggutor = Executors.newFixedThreadPool(1)
        notificationAdapter = NotificationAdapter(this, list, requireActivity())

        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.recyclerViewNotification.adapter = notificationAdapter


        bind.trashBtn.setOnClickListener {
            var i = 0
            i = viewm.deleteNotification(selected_notification.id, user_nick)
            if(i > 0){
                this.updateView()
                Utilities.showaToast (
                    requireContext(),
                    "Notifica eliminata"
                )
            }
            bind.notificationSpecificFrameLayout.visibility = View.GONE
        }

        bind.addToCalendarBtn.setOnClickListener {
            //TODO: creare il coso dei permesi per poter scrivere e leggere il calendario
            
            addToCalendar(
                DateTime.parse(selected_notification.data_inizio, dateTime_formatter).millis,
                if(selected_notification.data_fine == null) null else DateTime.parse(selected_notification.data_fine, dateTime_formatter).millis,
                selected_notification.title!!,
                selected_notification.content!!
            )
            bind.notificationSpecificFrameLayout.visibility = View.GONE
        }

        bind.notificationFab.setOnClickListener {
            var i = 0
            i = viewm.readAllNotification(user_nick)

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
        selected_notification = list[position]
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
        if(
            (list[position].data_inizio == null && list[position].data_fine == null)
            ||
            (list[position].data_inizio == null)
            ){
            bind.addToCalendarBtn.visibility = View.GONE
        } else {
            bind.addToCalendarBtn.visibility = View.VISIBLE
        }
        this.updateView()
    }

    fun updateView(){
        notificationAdapter.updateView(viewm.getNotification(user_nick))
    }


    fun addToCalendar(dataInizio: Long, dataFine: Long? = null, title: String, description: String){

        if(dataFine == null){ // solo data inizio
            val values = ContentValues()
            values.put(CalendarContract.Events.DTSTART, dataInizio)
            values.put(CalendarContract.Events.DURATION, "+P1H") // Evento di un'ora
            values.put(CalendarContract.Events.TITLE, title)
            values.put(CalendarContract.Events.CALENDAR_ID, 1)
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
            requireContext().contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        } else { //range
            val values = ContentValues()
            values.put(CalendarContract.Events.DTSTART, dataInizio)
            values.put(CalendarContract.Events.DTEND, dataFine)
            values.put(CalendarContract.Events.TITLE, title)
            values.put(CalendarContract.Events.DESCRIPTION, attr.description)
            values.put(CalendarContract.Events.CALENDAR_ID, 1)
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)

            requireContext().contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        }
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Utilities.CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addToCalendar()
                } else {
                    // L'utente ha negato il permesso per la fotocamera, gestisci di conseguenza
                    // Ad esempio, mostra un messaggio all'utente
                    MaterialAlertDialogBuilder(
                        requireContext()
                    )
                        .setTitle("Permissione Denied")
                        .setMessage("How is possible for you to face your enemy without a face")
                }
            }
            Utilities.GALLERY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    add()
                } else {
                    // L'utente ha negato il permesso per la galleria, gestisci di conseguenza
                    // Ad esempio, mostra un messaggio all'utente
                    MaterialAlertDialogBuilder(
                        requireContext()
                    )
                        .setTitle("Permissione Denied")
                        .setMessage("How is possible for you to face your enemy without a face")
                }
            }
        }

    }*/

    override fun onDestroy() {
        super.onDestroy()
        exeggutor.shutdown()
    }
}