package com.example.lordofthegames.home

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentNotificationBinding
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.recyclerView.NotificationAdapter
import com.example.lordofthegames.recyclerView.OnItemListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
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

        bind.closeBtn.setOnClickListener {
            bind.notificationSpecificFrameLayout.visibility = View.GONE
        }


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

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_CALENDAR
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_CALENDAR
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("LOCALENDARIAZZIO", "$selected_notification")
                (if(selected_notification.data_fine == null) null else DateTime.parse(selected_notification.data_fine, dateTime_formatter).millis)?.let { it1 ->
                    addEventToCalendar(
                        title = selected_notification.title!!,
                        description = selected_notification.content!!,
                        begin = DateTime.parse(selected_notification.data_inizio, dateTime_formatter).millis,
                        end = it1,

                        )
                }

                bind.notificationSpecificFrameLayout.visibility = View.GONE
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.READ_CALENDAR
                    ),
                    1
                )
            }


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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Utilities.WRITE_CALENDAR_CODE -> {
                // Se la richiesta è annullata, i risultati degli array sono vuoti
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // I permessi sono stati concessi, puoi chiamare la funzione per creare un evento
                    addToCalendar(
                        DateTime.parse(selected_notification.data_inizio, dateTime_formatter).millis,
                        if(selected_notification.data_fine == null) null else DateTime.parse(selected_notification.data_fine, dateTime_formatter).millis,
                        selected_notification.title!!,
                        selected_notification.content!!
                    )
                    bind.notificationSpecificFrameLayout.visibility = View.GONE
                } else {
                    MaterialAlertDialogBuilder(
                        requireContext()
                    )
                        .setTitle("Permissione Denied")
                        .setMessage("No calendario no party")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Utilities.WRITE_CALENDAR_CODE -> {
                    Utilities.showaToast(requireContext(), "Evento aggiunto, va a controllare")
                }
                Utilities.READ_CALENDAR_CODE -> {
                    Utilities.showaToast(requireContext(), "Evento aggiunto, va a controllare")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exeggutor.shutdown()
    }

    private fun addToCalendar(dataInizio: Long, dataFine: Long? = null, title: String, description: String){
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, dataInizio)
        if(dataFine != null) values.put(CalendarContract.Events.DTEND, dataFine) else values.put(CalendarContract.Events.DURATION, "+P1H")
        values.put(CalendarContract.Events.TITLE, title)
        values.put(
            CalendarContract.Events.CALENDAR_ID,
            Utilities.getCalendarId(requireContext()) ?: 1
        )
        values.put(CalendarContract.Events.DESCRIPTION, attr.description)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        val uri = requireContext().contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

        if (uri != null) {
            val eventID = uri.lastPathSegment!!.toLong()
            // L'evento è stato creato con successo
            Utilities.showaToast(requireContext(), "Evento creato con ID: $eventID")
        } else {
            // L'inserimento dell'evento è fallito
            Utilities.showaToast(requireContext(), "Errore nella creazione dell'evento")
        }
    }

    fun addEventToCalendar(title: String, description: String, begin: Long, end: Long) {
        val intent: Intent = Intent(Intent.ACTION_INSERT)
            .setData(Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
            .putExtra(Events.TITLE, title)
            .putExtra(Events.DESCRIPTION, description)
            .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("email", "sessp"))
        startActivity(intent)
    }


}