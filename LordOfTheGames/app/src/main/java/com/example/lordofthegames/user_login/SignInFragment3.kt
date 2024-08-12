package com.example.lordofthegames.user_login

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.Address
import android.location.Geocoder
import android.location.GpsStatus
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.BuildConfig
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentSigninLocationBinding
import com.example.lordofthegames.db_entities.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.IOException
import java.util.Locale


class SignInFragment3: Fragment(), MapListener, GpsStatus.Listener {

    private lateinit var bind: FragmentSigninLocationBinding


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var posizioneSalvata: String = ""
    private var formattedPosition: String = ""

    private lateinit var loggedViewModel: LoggedViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSigninLocationBinding.inflate(layoutInflater, container, false);
        loggedViewModel = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        this.setUpMap()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bind.txtFlag.text = Utilities.toFlagEmoji("RN")


        bind.posizzionatp.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    permission.ACCESS_FINE_LOCATION,
                    permission.ACCESS_COARSE_LOCATION,
                ),
                1
            )

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ) {
                this.prendiPosizione()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        permission.ACCESS_FINE_LOCATION,
                        permission.ACCESS_COARSE_LOCATION,
                    ),
                    1
                )
            }
        }

        bind.salvaAccount.setOnClickListener {
            this.signin()
        }

    }

    private fun prendiPosizione() {
        Utilities.enableGPS(requireContext(), requireActivity() as AppCompatActivity)

        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(permission.ACCESS_COARSE_LOCATION), 1
        )
        val client = LocationServices
            .getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext(), permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        client.lastLocation.addOnSuccessListener(
            requireActivity() ) { location: Location? ->
            if (location != null) {
                posizioneSalvata = Uri.parse(
                    "http://maps.google.com/maps?q=loc:"
                            + location.latitude + ',' + location.longitude
                ).toString()
                bind.textview111.text = "lat: ${location.latitude}, long: ${location.longitude}"
                val l = Utilities.getCountryNameAndCode(requireContext(), location.latitude, location.longitude)
                bind.testoPosizione.text = "Quindi vieni dal...\n${l?.get(0)}"
                bind.txtFlag.text = Utilities.toFlagEmoji(l?.get(1)!!)
                this.setUpMap()
                bind.salvaAccount.visibility = View.VISIBLE
                formattedPosition = "{\"abbr\":\"${l[1]}\", \"lungo\":\"$posizioneSalvata\"}"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Utilities.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
        }
    }

    private fun getCountryAbbreviation(location: Location): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: MutableList<Address>? =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                return addresses[0].countryCode
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "N/A"
    }

    fun signin() {

        val sp: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val res = loggedViewModel
            .insertNewUsr(
                User(
                    requireArguments().getString("mail", "BANANA"),
                    requireArguments().getString("nick", "BANANA"),
                    requireArguments().getString("passw", "BANANA"),
                    requireArguments().getString("uimg", "BANANA"),
                    formattedPosition
                )
            )

        if(res > 0){
            val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            banana.edit()
                .putString("logged", "logged")
                .putString("nickname", requireArguments().getString("nick", "BANANA"))
                .putString("email", requireArguments().getString("mail", "BANANA"))
                .apply()

            //parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, LoggedInFragment()).addToBackStack(null).commit()
            startActivity(
                Intent(
                    requireContext(),
                    MainActivity::class.java
                )
            )
        } else {
            Utilities.showaToast(requireContext(), "Le forse del male sono incerte")
        }

    }


    private fun setUpMap(){
        val map = bind.osmmap
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.mapCenter
        map.setMultiTouchControls(true)
        map.getLocalVisibleRect(Rect())

        var controller: IMapController = map.controller
        var locationOverlay: MyLocationNewOverlay =
            MyLocationNewOverlay(
                GpsMyLocationProvider(
                    requireContext()
                ), map
            )

        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        locationOverlay.isDrawAccuracyEnabled = true
        /*locationOverlay.runOnFirstFix {
            runOnUiThread {
                controller.setCenter(locationOverlay.myLocation);
                controller.animateTo(locationOverlay.myLocation)
            }
        }*/
        controller.setZoom(6.0)
        map.overlays.add(locationOverlay)

        map.addMapListener(this)


    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.mapCenter?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.mapCenter?.longitude}")
        bind.textview111.text = "lat: ${event?.source?.mapCenter?.latitude}, long: ${event?.source?.mapCenter?.longitude}"
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    /*override fun onLocationChanged(location: Location) {
        Utilities.showaToast(
            requireContext(),
            "latitude = ${location.latitude * 1e6} longitude = ${location.longitude * 1e6}",
            )

        val point = GeoPoint((location.latitude * 1E6), (location.longitude * 1E6))

        bind.textview111.text = "latitude = ${location.latitude * 1e6} longitude = ${location.longitude * 1e6}"

        Utilities.showaToast(
            requireContext(),
            point.toString()
        )
    }*/

    override fun onGpsStatusChanged(p0: Int) {
        bind.textview111.text = bind.textview111.text.toString() + " $p0"
    }

    /*
    * ROBE
    * */


}