package com.example.lordofthegames

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.UiModeManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.CalendarContract.Calendars
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Community.CommunitySpecificFragment
import com.example.lordofthegames.Database.LOTGDatabase
import com.example.lordofthegames.GameDetails.GameDetFragment
import com.example.lordofthegames.Settings.SettingsActivity
import com.example.lordofthegames.Settings.SettingsFragment
import com.example.lordofthegames.db_entities.Notification
import com.example.lordofthegames.home.HomeFragment
import com.example.lordofthegames.home.NotificationViewModel
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.example.lordofthegames.user_login.LoggedActivity
import com.example.lordofthegames.user_login.LoggedInFragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import kotlin.Pair


class Utilities {

    /*
    * TODO: ULTIMATE
    *  Funzionalità obbligatorie
        2. Profilo utente
            2. GPS (si può usare semplicemente per la posizione utente, es. casa)
        *
     * Funzionalità opzionali
        • Progettazione / Design (max 2)
            – mockup (potete usare diversi software, come balsamiq, figma, AdobeXD, ecc.. e in formato cartaceo eventualmente)
        • Mappa (max 2)
            – se non previsto dalle funzionalità dell'app si può visualizzare la posizione dell'utente nel profilo, potete utilizzare sia OpenStreetMap che Google Map
        • Intent per chiamata/calendario o simili (max 2)
            – uno di questi: https://developer.android.com/guide/components/intents-common - preferibilmente non visto a lezione
        • Tracking utente su mappa (max 4) <- boh
    * */

    companion object{
        const val CAMERA_PERMISSION_REQUEST_CODE = 101
        const val GALLERY_PERMISSION_REQUEST_CODE = 102
        const val CAMERA_REQUEST_CODE = 103
        const val GALLERY_REQUEST_CODE = 104
        const val CAMERA_AND_GALLERY_REQUEST_CODE = 123
        const val REQUEST_WRITE_STORAGE = 106
        const val READ_CALENDAR_CODE  = 100
        const val WRITE_CALENDAR_CODE = 100
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val REQUEST_CHECK_SETTINGS = 1001

        fun insertFragment(activity: AppCompatActivity, fragment: Fragment, tag: String, bundle: Bundle?){


            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            fragment.arguments = bundle
            transaction.replace(R.id.fragment_container_view, fragment, tag)

            if (
                fragment !is HomeFragment &&
                fragment !is GameDetFragment &&
                fragment !is SettingsFragment &&
                fragment !is LoggedInFragment &&
                fragment !is CommunitySpecificFragment //&&
                //fragment !is DiscussionCreateFragment
            ) {
                transaction.addToBackStack(tag);
            }

            transaction.commit()
        }

        fun setUpDrawer(
            drawerLayout: DrawerLayout,
            navigationView: NavigationView,
            activity: AppCompatActivity,
            img: Drawable? = null
        ): ActionBarDrawerToggle {

            val actionBarDrawerToggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.belandih, R.string.besughi)
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            drawerLayout.closeDrawers()

            val sp: SharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            val header_mail: TextView? = drawerLayout.findViewById(R.id.mail_header)
            val header_nick: TextView? = drawerLayout.findViewById(R.id.nickname_header)
            val header_img: ImageView? = drawerLayout.findViewById(R.id.usr_icon)
            if (header_mail != null) {
                header_mail.text = sp.getString("email", "BELANDIH@paperissima.sprint")
            }
            if (header_nick != null) {
                header_nick.text = sp.getString("nickname", "BESUGHI")
            }
            if(header_img != null && img != null) {
                header_img.setImageDrawable(img)
            }


            if(activity.javaClass == SettingsActivity::class.java){
                navigationView.menu.findItem(R.id.nav_setting).isVisible = false
            }else if(activity.javaClass == LoggedActivity::class.java){
                navigationView.menu.findItem(R.id.nav_usr).isVisible = false
            }

            return actionBarDrawerToggle
        }


        /**
         * Utility to convert a drawable into a bitmap (to store the android icon as a bitmap)
         * @param drawable the drawable of the android icon
         * @return the bitmap of the drawable
         */
        fun drawableToBitmap(drawable: Drawable): Bitmap? {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        fun setUpToolBar(
            activity: AppCompatActivity,
            toolbar: Toolbar,
            title: String?,
            drawerLayout: DrawerLayout?,
            menu: Int?,
            img: BitmapDrawable? = null
        ) {
            val imggg = img
            if (menu != null) {
                toolbar.inflateMenu(menu)
            }

            toolbar.title = title
            if (img != null) {
                toolbar.setNavigationIcon(img)
            } else {
                toolbar.setNavigationIcon(R.mipmap.gabibbo_blade_of_striscia)
            }


            if(drawerLayout != null) {

                val sp: SharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                toolbar.setNavigationOnClickListener {

                    val actionBarDrawerToggle = ActionBarDrawerToggle( //porcoddio
                        activity,
                        drawerLayout,
                        R.string.belandih,
                        R.string.besughi
                    )
                    actionBarDrawerToggle.isDrawerIndicatorEnabled = false
                    toolbar.setNavigationIcon(R.drawable.ic_t_pose)
                    drawerLayout.addDrawerListener(actionBarDrawerToggle)
                    actionBarDrawerToggle.syncState()
                }
            }
            activity.setSupportActionBar(toolbar)


        }

        fun createDatabase(context: Context) {

            val database by lazy { LOTGDatabase.getDatabase(context) }


        }



        fun darkThemeSwitch(context: Context, sharedPreferences: SharedPreferences): Boolean{
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            val mode = uiModeManager.nightMode
            val isDarkTheme = sharedPreferences.getBoolean("theme", mode == UiModeManager.MODE_NIGHT_YES)
            sharedPreferences.edit().putBoolean("theme", mode == UiModeManager.MODE_NIGHT_YES).apply()
            return isDarkTheme
        }

        fun rainbowBG(): GradientDrawable{
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BL_TR, intArrayOf(
                    R.color.rosso,
                    R.color.arancione,
                    R.color.giallo,
                    R.color.verde1,
                    R.color.verde2,
                    R.color.verde3,
                    R.color.azzurro1,
                    R.color.azzurro2,
                    R.color.blu,
                    R.color.viola1,
                    R.color.viola2,
                    R.color.viola3,
                    )
            )
            gradientDrawable.gradientType = GradientDrawable.SWEEP_GRADIENT
            gradientDrawable.setGradientCenter(0.5f, 0.5f)
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            return gradientDrawable
        }

        fun signin(activity: AppCompatActivity, mail: String, nick: String, password: String){
            //aggiungere entry al db e loggare aggiungendo allo sharedpreference nick, mail e logged
            sharedPrefLog(activity, nick, mail)
        }

        fun login(activity: AppCompatActivity, mail: String, nick: String, password: String){
            //aggiungere controllo sulle entry prese dal db usate con la query SELECT nick, mail FROM User where password=${password} e poi aggiungere tutto allo sharedpref
            sharedPrefLog(activity, nick, mail)
        }

        fun checkLogin(): Boolean{
            //("METTERE ROBA")
            return false
        }


        fun generateSalt(): String{
            val random = SecureRandom()
            val salt = ByteArray(16) // 16 bytes = 128 bits
            random.nextBytes(salt)
            return salt.toString()
        }

        fun hashPassword(password: String, salt: ByteArray): String {
            val sha256 = MessageDigest.getInstance("SHA-256")
            val hashedSalt = sha256.digest(salt)
            return hashedSalt.toString()
        }

        fun sharedPrefLog(activity: AppCompatActivity, nick: String, mail: String){
            val sp = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
            sp.putString("logged", "")
            sp.putString("mail", mail)
            sp.putString("nick", nick)
            sp.apply()
        }




        fun saveImage(contentResolver: ContentResolver, capturedImageUri: Uri) {
            val bitmap = getBitmap(capturedImageUri, contentResolver)

            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${SystemClock.uptimeMillis()}")

            val imageUri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            val outputStream = imageUri?.let { contentResolver.openOutputStream(it) }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.close()
        }

        fun Context.createImageFile(): File {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            return File.createTempFile(
                imageFileName,
                ".jpg",
                externalCacheDir
            )
        }

        fun getBitmap(selectedPhotoUri: Uri, contentResolver: ContentResolver): Bitmap {
            val bitmap = when {
                Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    selectedPhotoUri
                )
                else -> {
                    val source = ImageDecoder.createSource(contentResolver, selectedPhotoUri)
                    ImageDecoder.decodeBitmap(source)
                }
            }
            return bitmap
        }

        /**
         * Converts bitmap to byte array in PNG format
         * @param bitmap source bitmap
         * @return result byte array
         */
        fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray? {
            var baos: ByteArrayOutputStream? = null
            return try {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                baos.toByteArray()
            } finally {
                if (baos != null) {
                    try {
                        baos.close()
                    } catch (e: IOException) {
                        Log.e(
                            "BitmapUtils",
                            "ByteArrayOutputStream was not closed"
                        )
                    }
                }
            }
        }

        /**
         * Converts bitmap to the byte array without compression
         * @param bitmap source bitmap
         * @return result byte array
         */
        fun convertBitmapToByteArrayUncompressed(bitmap: Bitmap): ByteArray? {
            val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
            bitmap.copyPixelsToBuffer(byteBuffer)
            byteBuffer.rewind()
            return byteBuffer.array()
        }

        /**
         * Converts compressed byte array to bitmap
         * @param src source array
         * @return result bitmap
         */
        fun stringToByteArrayToBitmap(base64String: String): Bitmap {
            val decodedByteArray: ByteArray = Base64.getDecoder().decode(base64String)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }


        /**PORCODIO**/
        fun bitmapToString(bitmap: Bitmap, quality: Int = 50): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.getEncoder().encodeToString(byteArray)
        }

        fun stringToBitmap(encodedString: String?): Bitmap? {
            try {
                val decodedString = Base64.getDecoder().decode(encodedString)
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                return null
            }
        }

        /**PORCODIO**/



        fun showaToast(context: Context, testo: String){
            val duration = Toast.LENGTH_SHORT

            Toast.makeText(context, testo, duration).show()

        }



        fun generaNotifiche(context: FragmentActivity, notification_id:Int, textTitle: String, textContent: String, data_inizio: String, data_fine: String, CHANNEL_ID: String, usr_ref: String) {

            // Verifica se l'applicazione ha il permesso di inviare notifiche
            if (ActivityCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Richiedi il permesso all'utente
                ActivityCompat.requestPermissions(context, arrayOf(POST_NOTIFICATIONS), 500)
                return
            } else {

                // Costruisci la notifica
                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.yo_listen_foreground)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Se l'API level è 26 o superiore, crea e registra il canale delle notifiche
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelName = "My Notification Channel"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                        description = "Description of my notification channel"
                        // Configura altre opzioni del canale, se necessario
                        enableLights(true)
                        lightColor = Color.RED
                    }

                    // Registra il canale delle notifiche con il NotificationManager
                    val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val n = ViewModelProvider(context)[NotificationViewModel::class.java]


                n.saveNotification(
                    Notification(
                        notification_id,
                        textTitle,
                        textContent,
                        data_inizio,
                        data_fine,
                        0,
                        usr_ref
                    )
                )

                // Invia la notifica utilizzando il NotificationManagerCompat
                with(NotificationManagerCompat.from(context)) {
                    notify(666, builder.build())
                }

            }

        }
        fun TUDEI(): String {
            val d = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                //.forPattern("dd/MM/yyyy'T'HH:mm:ssZ")
                .parseLocalDateTime(
                    DateTime
                        .now()
                        .toString()
                )

            return "${d.dayOfMonth}/${d.monthOfYear}/${d.year} - ${d.hourOfDay+1}:${d.minuteOfHour}:${d.secondOfMinute}"
        }


        fun setupPieChart(pieChart: PieChart, graphData: UserGameGraphItem, darkTheme: Boolean) {

            //pupulating list of PieEntires
            val pieEntires: MutableList<PieEntry> = ArrayList()

            /*pieEntires.add(
                PieEntry( statistics.gameNumTot.toFloat(), "Tot")
            )*/
            pieEntires.add(
                PieEntry( graphData.playingTot.toFloat(), "Playing")
            )
            pieEntires.add(
                PieEntry( graphData.planToPlayTot.toFloat(), "Planned")
            )
            pieEntires.add(
                PieEntry( graphData.abandonedTot.toFloat(), "Abandoned")
            )
            pieEntires.add(
                PieEntry(graphData.completedTot.toFloat(), "Completed")
            )

            val dataSet = PieDataSet(pieEntires, "")

            //dataSet.setColors(*MATERIAL_COLORS)

            dataSet.setColors(
                rgb("#00AC88"),
                rgb("#007005"),
                rgb("#FF6200EE"),
                rgb("#970000")
            )

            val data = PieData(dataSet)
            //Get the chart
            pieChart.data = data
            //pieChart.isDrawHoleEnabled = false;
            pieChart.invalidate()
            pieChart.centerText = "Giochi totali: \n ${graphData.gameNumTot} \n "
            pieChart.setDrawEntryLabels(false)
            pieChart.contentDescription = ""
            data.setValueTextSize(15f);
            pieChart.setCenterTextSize(15f)
            pieChart.holeRadius = 35F;
            pieChart.maxHighlightDistance = 74f;

            //legend attributes
            val legend: Legend = pieChart.legend
            legend.form = Legend.LegendForm.CIRCLE
            legend.textSize = 12f
            legend.formSize = 20f
            legend.formToTextSpace = 2f

            if(darkTheme)
                legend.textColor = Color.WHITE
            else
                legend.textColor = Color.BLACK
        }


        fun setDrawerWithUser(headerView: NavigationView, nick: String, mail: String, img: String?, position: String){
            val head = headerView.getHeaderView(0)
            val usr_icon: ImageView = head.findViewById(R.id.usr_icon)
            val nick_head: TextView = head.findViewById(R.id.nickname_header)
            val mail_head: TextView = head.findViewById(R.id.mail_header)

            val js = this.translateUserPosition(position)
            nick_head.text = "$nick ${
                if(js.isNotEmpty()) js.get("abbr")?.let { this.toFlagEmoji(it) } else "RN"
            }"
            mail_head.text = mail
            if(img != null) usr_icon.setImageBitmap(this.stringToByteArrayToBitmap(img))
        }

        fun getCalendarId(context: Context) : Long? {
            val projection = arrayOf(Calendars._ID, Calendars.CALENDAR_DISPLAY_NAME)

            var calCursor = context.contentResolver.query(
                Calendars.CONTENT_URI,
                projection,
                Calendars.VISIBLE + " = 1 AND " + Calendars.IS_PRIMARY + "=1",
                null,
                Calendars._ID + " ASC"
            )

            if (calCursor != null && calCursor.count <= 0) {
                calCursor = context.contentResolver.query(
                    Calendars.CONTENT_URI,
                    projection,
                    Calendars.VISIBLE + " = 1",
                    null,
                    Calendars._ID + " ASC"
                )
            }

            if (calCursor != null) {
                if (calCursor.moveToFirst()) {
                    val calName: String
                    val calID: String
                    val nameCol = calCursor.getColumnIndex(projection[1])
                    val idCol = calCursor.getColumnIndex(projection[0])

                    calName = calCursor.getString(nameCol)
                    calID = calCursor.getString(idCol)

                    Log.d("CALENDERAZZIO", "Calendar name = $calName Calendar ID = $calID")

                    calCursor.close()
                    return calID.toLong()
                }
            }
            return null
        }

        fun generaNotificaNonSalvabile(
            context: FragmentActivity,
            title: String,
            content: String,
            CHANNEL_ID: String
        ) {

            // Verifica se l'applicazione ha il permesso di inviare notifiche
            if (ActivityCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Richiedi il permesso all'utente
                ActivityCompat.requestPermissions(context, arrayOf(POST_NOTIFICATIONS), 500)
                return
            } else {

                // Costruisci la notifica
                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.yo_listen_foreground)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Se l'API level è 26 o superiore, crea e registra il canale delle notifiche
                val channelName = "My Notification Channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                    description = "Description of my notification channel"
                    // Configura altre opzioni del canale, se necessario
                    enableLights(true)
                    lightColor = Color.RED
                }

                // Registra il canale delle notifiche con il NotificationManager
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // Invia la notifica utilizzando il NotificationManagerCompat
                with(NotificationManagerCompat.from(context)) {
                    notify(666, builder.build())
                }

            }
        }


        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        fun isNetworkConnected2(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            if (network != null) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                return networkCapabilities != null &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
            return false
        }

        /**
         * This method is to change the country code like "us" into 🇺🇸
         * Stolen from https://stackoverflow.com/a/35849652/75579
         * 1. It first checks if the string consists of only 2 characters: ISO 3166-1 alpha-2 two-letter country codes (https://en.wikipedia.org/wiki/Regional_Indicator_Symbol).
         * 2. It then checks if both characters are alphabet
         * do nothing if it doesn't fulfil the 2 checks
         * caveat: if you enter an invalid 2 letter country code, say "XX", it will pass the 2 checks, and it will return unknown result
         */
        fun toFlagEmoji(txt: String): String {

            // 1. It first checks if the string consists of only 2 characters: ISO 3166-1 alpha-2 two-letter country codes (https://en.wikipedia.org/wiki/Regional_Indicator_Symbol).
            if (txt.length != 2) {
                return txt
            }

            val countryCodeCaps = txt.toUpperCase(Locale.ROOT) // upper case is important because we are calculating offset
            val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

            // 2. It then checks if both characters are alphabet
            if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
                return txt
            }

            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }

        fun checkAndEnableGPS(context: Context, activity: AppCompatActivity) {
            if (activity.getSystemService(Context.LOCATION_SERVICE) != null) {
                // GPS non attivo, mostriamo un dialogo per attivarlo
                AlertDialog.Builder(activity)
                    .setMessage("GPS is disabled. Do you want to enable it?")
                    .setPositiveButton(
                        "Yes"
                    ) { _, _ -> // Apri le impostazioni per attivare il GPS
                        this.enableGPS(context, activity)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }

        fun enableGPS(context: Context, activity: AppCompatActivity) {
            val locationRequest: LocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000)

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val result: Task<LocationSettingsResponse> =
                LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

            result.addOnCompleteListener { task ->
                try {
                    val response = task.getResult(ApiException::class.java)
                    // GPS is already enabled, perform any action if needed
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            val resolvable = exception as ResolvableApiException
                            resolvable.startResolutionForResult(
                                activity,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->// GPS settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.
                            Toast.makeText(
                                context,
                                "GPS cannot be enabled.",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }

        fun getCountryNameAndCode(
            context: Context,
            latitude: Double,
            longitude: Double
        ): Array<String?>? {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                // Ottieni una lista di indirizzi dalle coordinate
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    // Prendi il primo indirizzo della lista
                    val address = addresses[0]
                    val countryName = address.countryName // Nome completo della nazione
                    val countryCode = address.countryCode // Codice ISO 3166-1 alpha-2
                    return arrayOf(countryName, countryCode)
                } else {
                    // Se non è stato trovato nessun indirizzo
                    return null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        fun translateUserPosition(position: String?): Map<String, String>{
            return if(position != null) {
                val jsonObject = JSONObject(position)
                mapOf(
                    Pair("abbr", jsonObject.getString("abbr")),
                    Pair("lungo", jsonObject.getString("lungo")),
                )
            } else {
                mapOf()
            }
        }


    }
}