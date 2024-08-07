package com.example.lordofthegames.user_login

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentSigninLocationBinding
import com.example.lordofthegames.db_entities.User
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale


class SignInFragment3: Fragment() {

    private lateinit var bind: FragmentSigninLocationBinding

    private lateinit var loggedViewModel: LoggedViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSigninLocationBinding.inflate(layoutInflater, container, false);
        loggedViewModel = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*bind.posizzionatp.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                1
            )

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                this.prendiPosizione()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                    1
                )
            }
        }*/

    }

    private fun prendiPosizione() {
        TODO("Prenderla sta position per ottenere solo la provenienza dell'untemte")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Utilities.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
        }
    }



    fun getPositionFormatted(): String? {

        var corto: String? = null
        var lungo: String? = null

        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(permission.ACCESS_FINE_LOCATION), 1
        )
        val client = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireActivity(), permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        client.lastLocation.addOnSuccessListener(
            requireActivity()
        ) { location ->
            if (location != null) {
                Log.i("LOCATION", "$location")
                lungo = Uri.parse(
                    "http://maps.google.com/maps?q=loc:"
                            + location.latitude + ',' + location.longitude
                ).toString()

                corto = getCountryAbbreviation(location)
            }
        }

         return if(corto != null && lungo != null) "{\"abbr\":$corto, \"pos\":$lungo}" else null
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

    fun signin(position: String) {

        val sp: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val res = loggedViewModel
            .insertNewUsr(
                User(
                    requireArguments().getString("mail", "BANANA"),
                    requireArguments().getString("nick", "BANANA"),
                    requireArguments().getString("passw", "BANANA"),
                    requireArguments().getString("uimg", "BANANA"),
                    position
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

    /*
    *
    * */
}