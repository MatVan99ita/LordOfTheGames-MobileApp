package com.example.lordofthegames.user_login

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.Manifest
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.example.lordofthegames.Manifest.permission
import com.example.lordofthegames.db_entities.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Base64

class LoggedInFragment: Fragment(){

    private lateinit var bind: FragmentLoggedinBinding
    private lateinit var viewm: LoggedViewModel
    private lateinit var statistics: UserGameGraphItem
    private lateinit var circularProgress: CircularProgressIndicator
    private var bundle: Bundle? = null
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentLoggedinBinding.inflate(layoutInflater, container, false);
        viewm = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]

        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        statistics = viewm.getUserStatisticsCounts(requireArguments().getString("email", "sesso"))
        bind.btnExit.setOnClickListener { eschilo() }
        val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val p: PieChart = bind.chart
        user = viewm.getUsr(banana.getString("email", "mail")!!)

        Utilities.setupPieChart(p, statistics, banana.getString("Theme", "NoTheme").equals("Night"))

        bind.fottinnLogged.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                1
            )

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                    .setTitle("Upload Pictures Option")
                    .setMessage("How do you want to set your picture?")
                    .setPositiveButton(
                        "Gallery"
                    ) { _: DialogInterface?, _: Int ->
                        openGallery()
                    }
                    .setNegativeButton(
                        "Camera"
                    ) { _: DialogInterface?, _: Int ->
                        openCamera()
                    }
                    .show()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_MEDIA_IMAGES,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                    1
                )
            }
        }
    }

    fun eschilo(){
        val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove("nickname")
        editor.remove("email")
        editor.remove("logged")
        editor.apply()
        startActivity(
            Intent(
                requireContext(),
                MainActivity::class.java
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Utilities.CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
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
                    openGallery()
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

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //val drawable: Drawable? = ContextCompat.getDrawable(requireContext(),
        //    this.resources.getIdentifier("yo_listen_foreground", "mipmap", ""))
        var img: Bitmap? = null

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Utilities.CAMERA_REQUEST_CODE -> {
                    // L'immagine è stata catturata con successo dalla fotocamera
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    img = imageBitmap
                    // Fai qualcosa con l'immagine (es. mostrala in un'ImageView)
                    bind.loggedUsrImg.setImageBitmap(imageBitmap)
                }
                Utilities.GALLERY_REQUEST_CODE -> {
                    // L'immagine è stata selezionata dalla galleria
                    val selectedImageBitmap = data?.data //.extras?.get("data") as Bitmap
                    // Fai qualcosa con l'URI dell'immagine (es. caricala in un'ImageView)
                    img = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageBitmap)

                    bind.loggedUsrImg.setImageURI(selectedImageBitmap)
                }
            }

            bind.btnSaveChanges.setOnClickListener {
                val bundle: Bundle? = arguments
                val imgs = img?.let { Base64
                    .getEncoder()
                    .encodeToString(
                        Utilities
                            .convertBitmapToByteArray(it)
                    ) }
                viewm.updateUsrImg(imgs!!, user.mail)
            }
        }
    }
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        // when a scale gesture is detected, use it to resize the image
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            imageView.scaleX = mScaleFactor
            imageView.scaleY = mScaleFactor
            return true
        }
    }


}