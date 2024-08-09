package com.example.lordofthegames.user_login

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.GridLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.ViewModel.UserBadgeViewModel
import com.example.lordofthegames.databinding.FragmentLoggedinBinding
import com.example.lordofthegames.db_entities.User
import com.example.lordofthegames.recyclerView.UserGameGraphItem
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.Base64
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.max


class LoggedInFragment: Fragment(){

    private lateinit var bind: FragmentLoggedinBinding
    private lateinit var viewm: LoggedViewModel
    private lateinit var viewBadge: UserBadgeViewModel
    private lateinit var statistics: UserGameGraphItem
    private lateinit var circularProgress: CircularProgressIndicator
    private var bundle: Bundle? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var user: User
    private lateinit var defaultImg: Drawable
    private var isExpanded = true
    private lateinit var banana: SharedPreferences
    private var pos: Map<String, String> = mapOf()


    //TODO: aggiungere al fragment sotto le medaglie la posizione dell'utente salvata che possa essere modificata

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentLoggedinBinding.inflate(layoutInflater, container, false);
        viewm = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        viewBadge = ViewModelProvider(requireActivity())[UserBadgeViewModel::class.java]

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultImg = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_gabibbo_drawable)!!
        isExpanded = bind.expandableLayout.isVisible
        bind.menuButton.text = if(isExpanded) "Badge \u25B2" else "Badge \u25BC"
        statistics = viewm.getUserStatisticsCounts(requireArguments().getString("email", "sesso"))
        bind.btnExit.setOnClickListener { eschilo() }
        banana = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val p: PieChart = bind.chart
        user = viewm.getUsr(banana.getString("email", "mail")!!)


        if(user.photo != null) bind.loggedUsrImg.setImageBitmap(Utilities.stringToByteArrayToBitmap(
            user.photo!!
        )) else bind.loggedUsrImg.setImageDrawable(defaultImg)

        pos = Utilities.translateUserPosition(user.position)
        bind.loggedNick.text = "${user.nickname} ${ if(pos.isNotEmpty()) pos.get("abbr")?.let { Utilities.toFlagEmoji(it) } else "RN" }"
        bind.loggedMail.text = user.mail

        if(user.photo != null) {
            bind.loggedUsrImg.setImageBitmap (
                Utilities.stringToBitmap (
                    user.photo
                )
            )
            (if(pos.isNotEmpty()) pos["abbr"]?.let { Utilities.toFlagEmoji(it) } else "RN")?.let {
                Utilities.setDrawerWithUser(
                    requireActivity().findViewById<View>(R.id.nav_view) as NavigationView,
                    banana.getString("nickname", "BANANA").toString(),
                    banana.getString("email", "BANANA").toString(),
                    viewm.getUsrImg(banana.getString("email", "BANANA").toString()),
                    position = it
                )
            }
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

        bind.menuButton.setOnClickListener{
            if (isExpanded) {
                collapse(bind.expandableLayout)
                bind.menuButton.text = "Badge \u25BC"
            } else {
                expand(bind.expandableLayout)
                bind.menuButton.text = "Badge \u25B2"
            }
            isExpanded = !isExpanded
        }


        this.setBadge()

    }

    private fun setBadge() {
        val game_level = statistics.gameNumTot
        val played_level = statistics.completedTot
        val playing_level = statistics.playingTot
        val achievement_level = viewBadge.getCompletedAchievementCount(user_ref = user.mail)
        val disccussion_num_level = viewBadge.getDiscussionCount(user_ref = user.nickname)
        val disccussion_like_level = viewBadge.getLikeCount(user_ref = user.nickname)
        val comments_num_level = viewBadge.getCommentCount(user_ref = user.nickname)

        bind.gamelistUaTxt.text = "Giochi in lista: $game_level"
        bind.playingUaTxt.text = "Che stai giocando: $playing_level"
        bind.completedUaTxt.text = "Giochi completati: $played_level"
        bind.achievementUaTxt.text = "Achievement completati: $achievement_level"
        bind.discussionUaTxt.text = "Discussioni create $disccussion_num_level"
        bind.commentiUaTxt.text = "Commenti scritti: $comments_num_level"
        bind.likeUaTxt.text = "Like ricevuti: $disccussion_like_level"

        bind.gamelistUaImg.setImageResource(
            returnBadgeId(game_level)
        )
        bind.playingUaImg.setImageResource(
            returnBadgeId(playing_level)
        )
        bind.completedUaImg.setImageResource(
            returnBadgeId(played_level)
        )
        bind.achievementUaImg.setImageResource(
            returnBadgeId(achievement_level)
        )
        bind.discussionUaImg.setImageResource(
            returnBadgeId(disccussion_num_level)
        )
        bind.commentiUaImg.setImageResource(
            returnBadgeId(comments_num_level)
        )
        bind.likeUaImg.setImageResource(
            returnBadgeId(disccussion_like_level)
        )

    }

    private fun returnBadgeId(count: Int): Int{
        return if (count == 0 ) R.mipmap.m1_legno_foreground
        else if(count == 1) R.mipmap.m2_ferro_foreground
        else if(count == 2) R.mipmap.m3_rame_foreground
        else if(count == 3) R.mipmap.m4_bronzo_foreground
        else if(count == 4) R.mipmap.m5_silver_foreground
        else if(count == 5) R.mipmap.m6_oro_foreground
        else if(count == 6) R.mipmap.m7_diamante_foreground
        else if(count == 7) R.mipmap.m8_smeraldo_foreground
        else if(count == 8) R.mipmap.m9_platino_foreground
        else if(count == 9) R.mipmap.m10_immortal_1_foreground
        else R.mipmap.m10_immortal_2_foreground

        /*
            if(count in 11..20)
            if(count in 21..35)
            if(count in 36..50)
            if(count in 51..75)
            if(count in 76..100)
            if(count in 101..200)
            if(count in 201..300)
            if(count in 301..600)
            if(count in 601..900)
        */
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

                    (if(pos.isNotEmpty()) pos["abbr"]?.let { Utilities.toFlagEmoji(it) } else "RN")?.let {
                        Utilities.setDrawerWithUser(
                            requireActivity().findViewById<View>(R.id.nav_view) as NavigationView,
                            banana.getString("nickname", "BANANA").toString(),
                            banana.getString("email", "BANANA").toString(),
                            viewm.getUsrImg(banana.getString("email", "BANANA").toString()),
                            position = it
                        )
                    }
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

        // Postpone the calculation to ensure that layout and size calculations are done
        v.post {
            Log.e("AAAALTEZZA", "${v.height}")
            //val targetHeight = calculateGridLayoutHeight((v as GridLayout))
            val mAnimator = slideAnimator(0, 1811, v)
            mAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animator: Animator) {
                    bind.loggedinFragment.post {
                        bind.loggedinFragment.smoothScrollTo(0, v.bottom)
                    }
                }

                override fun onAnimationStart(animator: Animator) {}

                override fun onAnimationCancel(animator: Animator) {}

                override fun onAnimationRepeat(animator: Animator) {}
            })
            mAnimator.start()
        }
    }
    /*bind.loggedinFragment.post {
        bind.loggedinFragment.smoothScrollTo(0, v.bottom)
        }
    }*/

    private fun calculateGridLayoutHeight(gridLayout: GridLayout): Int {
        var totalHeight = 0
        val rowCount = (gridLayout.childCount + gridLayout.columnCount - 1) / gridLayout.columnCount

        for (i in 0 until rowCount) {
            var rowHeight = 0
            for (j in 0 until gridLayout.columnCount) {
                val index = i * gridLayout.columnCount + j
                if (index < gridLayout.childCount) {
                    val child = gridLayout.getChildAt(index)
                    child.measure(
                        View.MeasureSpec.makeMeasureSpec(
                            gridLayout.width / gridLayout.columnCount,
                            View.MeasureSpec.EXACTLY
                        ),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    rowHeight =
                        max(rowHeight.toDouble(), child.measuredHeight.toDouble()).toInt()
                }
            }
            totalHeight += rowHeight
        }

        // Add padding and margins
        totalHeight += (rowCount - 1) * gridLayout.paddingTop
        totalHeight += gridLayout.paddingTop + gridLayout.paddingBottom

        Log.e("AAAALTEZZA", "$totalHeight")
        return totalHeight
    }

    private fun collapse(v: View) {
        val finalHeight = v.height
        Log.e("AAAAAAAAAAAAALTEZZA", "$finalHeight")
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