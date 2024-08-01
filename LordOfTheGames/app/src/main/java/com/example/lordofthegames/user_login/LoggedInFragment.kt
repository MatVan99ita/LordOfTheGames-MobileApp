package com.example.lordofthegames.user_login

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.Base64
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates


class LoggedInFragment: Fragment(){

    private lateinit var bind: FragmentLoggedinBinding
    private lateinit var viewm: LoggedViewModel
    private lateinit var statistics: UserGameGraphItem
    private lateinit var circularProgress: CircularProgressIndicator
    private var bundle: Bundle? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var user: User
    private lateinit var defaultImg: Drawable
    private var isExpanded = true

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

        defaultImg = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_gabibbo_drawable)!!
        isExpanded = bind.expandableLayout.isVisible
        bind.menuButton.text = if(isExpanded) "Badge \u25B2" else "Badge \u25BC"
        statistics = viewm.getUserStatisticsCounts(requireArguments().getString("email", "sesso"))
        bind.btnExit.setOnClickListener { eschilo() }
        val banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val p: PieChart = bind.chart
        user = viewm.getUsr(banana.getString("email", "mail")!!)


        if(user.photo != null) bind.loggedUsrImg.setImageBitmap(Utilities.stringToByteArrayToBitmap(
            user.photo!!
        )) else bind.loggedUsrImg.setImageDrawable(defaultImg)

        bind.loggedNick.text = user.nickname
        bind.loggedMail.text = user.mail

        if(user.photo != null) {
            bind.loggedUsrImg.setImageBitmap (
                Utilities.stringToBitmap (
                    user.photo
                )
            )
        } else {
            bind.loggedUsrImg.setImageResource(
                R.mipmap.gabibbo_blade_of_striscia
            )
        }
        cameraExecutor = Executors.newFixedThreadPool(1)

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

        bind.menuButton.setOnClickListener(View.OnClickListener {
            if (isExpanded) {
                collapse(bind.expandableLayout)
                bind.menuButton.text = "Badge \u25BC"
            } else {
                expand(bind.expandableLayout)
                bind.menuButton.text = "Badge \u25B2"
            }
            isExpanded = !isExpanded
        })

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
                    bind.btnSaveChanges.visibility = View.VISIBLE
                    bind.btnAnnullaLogges.visibility = View.VISIBLE
                }
                Utilities.GALLERY_REQUEST_CODE -> {
                    // L'immagine è stata selezionata dalla galleria
                    val selectedImageBitmap = data?.data //.extras?.get("data") as Bitmap
                    // Fai qualcosa con l'URI dell'immagine (es. caricala in un'ImageView)
                    img = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageBitmap)

                    bind.loggedUsrImg.setImageURI(selectedImageBitmap)
                    bind.btnSaveChanges.visibility = View.VISIBLE
                    bind.btnAnnullaLogges.visibility = View.VISIBLE
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
                val i =viewm.updateUsrImg(imgs!!, user.mail)
                Utilities.showaToast(requireContext(),
                    if(i>0) "update successfull" else "errore update img"
                )
                if(i<=0) {
                    bind.btnSaveChanges.visibility = View.VISIBLE
                    bind.btnAnnullaLogges.visibility = View.VISIBLE
                } else {
                    bind.btnSaveChanges.visibility = View.GONE
                    bind.btnAnnullaLogges.visibility = View.GONE
                }
            }

            bind.btnAnnullaLogges.setOnClickListener {

                if(user.photo != null) bind.loggedUsrImg.setImageBitmap(Utilities.stringToByteArrayToBitmap(
                    user.photo!!
                )) else bind.loggedUsrImg.setImageDrawable(defaultImg)

                bind.btnSaveChanges.visibility = View.GONE
                bind.btnAnnullaLogges.visibility = View.GONE

            }

        }
    }



    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Utilities.CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, Utilities.GALLERY_REQUEST_CODE)

    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        // when a scale gesture is detected, use it to resize the image
        private var mScaleFactor = 1.0f
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            bind.loggedUsrImg.scaleX = mScaleFactor
            bind.loggedUsrImg.scaleY = mScaleFactor
            return true
        }
    }

    private fun expand(v: View) {
        v.visibility = View.VISIBLE
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(widthSpec, heightSpec)

        val mAnimator = slideAnimator(0, v.measuredHeight, v)
        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                bind.loggedinFragment.post {
                    bind.loggedinFragment.smoothScrollTo(0, bind.loggedinFragment.bottom)
                }
            }

            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
        mAnimator.start()
    }

    private fun collapse(v: View) {
        val finalHeight = v.height

        val mAnimator = slideAnimator(finalHeight, 0, v)

        mAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                v.visibility = View.GONE
            }

            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
        mAnimator.start()
    }

    private fun slideAnimator(start: Int, end: Int, v: View): ValueAnimator {
        val animator = ValueAnimator.ofInt(start, end)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.setDuration(300)

        animator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = v.layoutParams
            layoutParams.height = value
            v.layoutParams = layoutParams
        }
        return animator
    }


}