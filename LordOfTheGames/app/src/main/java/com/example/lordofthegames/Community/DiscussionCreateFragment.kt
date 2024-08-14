package com.example.lordofthegames.Community

import android.Manifest
import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentAddDiscussionBinding
import com.example.lordofthegames.db_entities.Notification
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Arrays
import java.util.Calendar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DiscussionCreateFragment: Fragment() {


    private lateinit var bind: FragmentAddDiscussionBinding
    private lateinit var viewm: DiscussionViewModel
    private var isAllFabsVisible = false
    private lateinit var cameraExecutor: ExecutorService
    private var isExpanded = false
    private var dataInizio: String? = null
    private var dataFine:   String? = null
    private var oraInizio:  String = "17:00"
    private var oraFine:    String = "17:00"
    private var positionUrl: String = ""

    private var c_img: String? = null
    private var game_id: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        bind = FragmentAddDiscussionBinding.inflate(layoutInflater, container, false);
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isExpanded = bind.positionExpandable.isVisible
        val interpolator = OvershootInterpolator()
        val game_title = arguments?.getString("game_title")
        game_id = viewm.getGameId(title = game_title)

        // Now set all the FABs and all the action name
        // texts as GONE

        bind.addPhotoFab.visibility = View.GONE;
        bind.addImageFab.visibility = View.GONE;
        bind.addImageActionText.visibility = View.GONE;
        bind.addPhotoActionText.visibility = View.GONE;

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        bind.fabAttachment.setOnClickListener {
            if (!isAllFabsVisible) {

                showAllAnimation(interpolator)
            } else {

                hideAllAnimation(interpolator)
            }
        }


        cameraExecutor = Executors.newFixedThreadPool(1)


        bind.addPhotoFab.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.CAMERA,
                ),
                1
            )

            if (
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.CAMERA
                    ),
                    1
                )
            }
        }


        bind.addImageFab.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                1
            )

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.READ_MEDIA_IMAGES,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                    1
                )
            }
        }

        bind.menuPositionButton.setOnClickListener{
            if (isExpanded) {
                collapse(bind.positionExpandable)
                bind.menuPositionButton.text = "Aggiungi dati evento \u25BC"
            } else {
                expand(bind.positionExpandable)
                bind.menuPositionButton.text = "Aggiungi dati evento \u25B2"
            }
            isExpanded = !isExpanded
        }

        bind.postImg.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Remove image?")
                .setPositiveButton("Yes"){ _: DialogInterface?, _: Int ->
                    bind.postImg.visibility = View.GONE
                    bind.postImg.setImageDrawable(
                        ContextCompat.getDrawable(requireActivity(), R.mipmap.ic_gabibbo_test)
                    )
                }
                .setNegativeButton("No"){ _: DialogInterface?, _: Int ->
                }
                .show()
        }

        bind.btnSavePost.setOnClickListener {
            if(!bind.etTitle.text.isNullOrEmpty() && !bind.etContent.text.isNullOrEmpty())
                this.createDiscussion()
            else {
                Utilities.showaToast(requireContext(), "Dovrai pur scrivere qualcosa no?")
            }
        }
        bind.btnNopePost.setOnClickListener { this.deleteDiscussion() }

        bind.getOIBtn.setOnClickListener { this.getOra(true) }
        bind.getOFBtn.setOnClickListener { this.getOra(false) }

        bind.getDIBtn.setOnClickListener { this.getData(true) }
        bind.getDFBtn.setOnClickListener { this.getData(false) }

        bind.gpsDiscussBtn.setOnClickListener {
            if(bind.manualPosition.text.isNotEmpty()){
                MaterialAlertDialogBuilder(
                    requireContext()
                )
                    .setTitle("Posizione manuale già insetita")
                    .setMessage(". Sovrascrivere con posizione attuale?")
                    .setPositiveButton("si") { _, _ -> this.checkGPSPermission() }
                    .setNegativeButton("Nouh") { _, _ -> }
                    .show()
            } else {
                this.checkGPSPermission()
            }
        }

        bind.manualPosition.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                bind.autoPosition.text = "Posizione attuale: \n ${p0.toString()}"
            }

        })

        bind.deleteEvent.setOnClickListener {
            bind.manualPosition.text.clear()
            bind.autoPosition.text = "Posizione attuale: "
            bind.getDIBtn.text = "dd-MM-yyyy"
            bind.getDFBtn.text = "dd-MM-yyyy"
            bind.getOIBtn.text = "00:00"
            bind.getOFBtn.text = "00:00"
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

                    this.c_img = Utilities.bitmapToString(img)

                    // Fai qualcosa con l'immagine (es. mostrala in un'ImageView)
                    bind.postImg.setImageBitmap(imageBitmap)

                    bind.postImg.visibility = View.VISIBLE
                    hideAllAnimation(OvershootInterpolator())
                }
                Utilities.GALLERY_REQUEST_CODE -> {

                    val selectedImageBitmap = data?.data

                    img = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageBitmap)


                    this.c_img =  Utilities.bitmapToString(img)
                    bind.postImg.setImageURI(selectedImageBitmap)
                    bind.postImg.visibility = View.VISIBLE
                    hideAllAnimation(OvershootInterpolator())
                }
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Utilities.CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, Utilities.GALLERY_REQUEST_CODE)

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


    private fun hideAllAnimation(interpolator: OvershootInterpolator){

        val interpolator = OvershootInterpolator()
        bind.addImageFab.hide()
        bind.addPhotoFab.hide()
        bind.addImageActionText.visibility = View.GONE
        bind.addPhotoActionText.visibility = View.GONE


        ViewCompat.animate(bind.fabAttachment)
            .rotation(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()




        ViewCompat.animate(bind.addPhotoFab)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()
        ViewCompat.animate(bind.addPhotoActionText)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()

        ViewCompat.animate(bind.addImageFab)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()
        ViewCompat.animate(bind.addImageActionText)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()

        // make the boolean variable false
        // as we have set the sub FABs
        // visibility to GONE
        isAllFabsVisible = false;
    }

    private fun showAllAnimation(interpolator: OvershootInterpolator){
        // when isAllFabsVisible becomes
        // true make all the action name
        // texts and FABs VISIBLE.


        //bind.fabAttachment.rotation = 90.0F
        ViewCompat.animate(bind.fabAttachment)
            .rotation(90f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()



        bind.addImageFab.show()
        bind.addPhotoFab.show()
        bind.addImageActionText.visibility = View.VISIBLE
        bind.addPhotoActionText.visibility = View.VISIBLE


        ViewCompat.animate(bind.addPhotoFab)
            .translationY(-300f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()
        ViewCompat.animate(bind.addPhotoActionText)
            .translationY(-300f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()

        ViewCompat.animate(bind.addImageFab)
            .translationY(-150f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()
        ViewCompat.animate(bind.addImageActionText)
            .translationY(-150f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(interpolator).start()


        // make the boolean variable true as
        // we have set the sub FABs
        // visibility to GONE
        isAllFabsVisible = true;
    }

    fun createDiscussion(){

        val sp: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val s = sp.getString("nickname", "besugo")!!

        if(bind.etTitle.text?.trim()?.equals("") == true){
            Utilities.showaToast(requireContext(), "Manca un titolo")
        }
        if(bind.etContent.text?.trim()?.equals("") == true){
            Utilities.showaToast(requireContext(), "Manca il contenuto")
        }

        var di: String? = null
        var df: String? = null
        if(dataInizio != null){
            di = "${dataInizio}-${oraInizio}"
        }
        if(dataFine != null){
            df = "${dataFine}-${oraFine}"
        }

        val disc_id = viewm.saveNewDiscussion(
            title = bind.etTitle.text?.toString()!!,
            content = bind.etContent.text?.toString()!!,
            usr = s,
            game = game_id,
            img = if(c_img != null) c_img else null,
            location = positionUrl,
            dataInizio = di,
            dataFine = df
        )

        if(di != null) { //TODO: aggiungere luogo al framelayout della notifica e la posizione nell'intent calendario
            //Prendo tutti gli utenti
            viewm.getAllUser().forEach { nick ->

                viewm.eventNotification(
                    Notification(
                        id=0,
                        title = "Evento: ${bind.etTitle.text?.toString()}",
                        content = bind.etContent.text?.toString(),
                        data_inizio = di,
                        data_fine = df,
                        position = positionUrl,
                        read = 0,
                        usr_ref = nick
                    )
                )
            }

            Utilities.generaNotificaNonSalvabile(
                context = requireActivity(),
                title = "Nuovo evento per ${arguments?.getString("game_title")}",
                content = "",
                CHANNEL_ID = CommunityActivity::class.java.simpleName
            )
        }





        val bundle = Bundle()
        bundle.putInt("discussion_id", disc_id.toInt())

        Utilities.insertFragment(
            requireActivity() as AppCompatActivity,
            DiscussionSpecificFragment(),
            DiscussionSpecificFragment::class.java.simpleName,
            bundle,
        )
    }

    fun deleteDiscussion() {
        //fuck go back

        fragmentManager?.popBackStack()


    }

    fun checkGPSPermission(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
            1
        )

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            this.auto_position()
        }
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                1
            )
            this.checkGPSPermission()
        }
    }

    fun auto_position(){
        Utilities.enableGPS(requireContext(), requireActivity() as AppCompatActivity)

        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1
        )
        val client = LocationServices
            .getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        client.lastLocation.addOnSuccessListener(
            requireActivity() ) { location: Location? ->
            if (location != null) {
                val s = "http://maps.google.com/maps?q=loc:${location.latitude},${location.longitude}"
                //posizioneSalvata = Uri.parse(s).toString()
                val l = Utilities.getCountryNameAndCode(requireContext(), location.latitude, location.longitude)
                bind.autoPosition.movementMethod = LinkMovementMethod.getInstance()
                bind.autoPosition.text = "Posizione attuale: \n$s"
                bind.manualPosition.setText(s)
                Linkify.addLinks(bind.autoPosition, Linkify.WEB_URLS)
                positionUrl = s
            }
        }
    }

    fun getData(b: Boolean){
        var s: String = ""
        val c = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        val yearm = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our text view.
                val m = if("${monthOfYear+1}".length < 2) "0${ monthOfYear +1}" else "${ monthOfYear +1}"
                val d = if("$dayOfMonth".length < 2) "0$dayOfMonth" else "$dayOfMonth"

                s = "$d/$m/$year"

                if(b){//DataInizio
                    dataInizio = s
                    bind.getDIBtn.text = s
                } else {//DataFine
                    if(dataInizio != null && Utilities.checkDate(dataInizio!!, s)) {
                        dataFine = s
                        bind.getDFBtn.text = s
                    } else {
                        Utilities.showaToast(requireContext(), "Wormhole non ammessi")
                    }
                }
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            yearm,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()


    }



    fun getOra(b: Boolean){
        var s: String = ""
        val c = Calendar.getInstance()

        // on below line we are getting our hour, minute.
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)

        // on below line we are initializing
        // our Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                // on below line we are setting selected
                // time in our text view.
                s = "$hourOfDay:$minute"

                if(b){//OraInizio
                    oraInizio = s
                    bind.getOIBtn.text = s
                } else {//OraFine
                    oraFine = s
                    bind.getOFBtn.text = s
                }
            },
            hour,
            minutes,
            false
        )
        // at last we are calling show to
        // display our time picker dialog.
        timePickerDialog.show()

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
                    bind.addDiscussionFragment.post {
                        bind.addDiscussionFragment.smoothScrollTo(0, v.bottom)
                    }
                }

                override fun onAnimationStart(animator: Animator) {}

                override fun onAnimationCancel(animator: Animator) {}

                override fun onAnimationRepeat(animator: Animator) {}
            })
            mAnimator.start()
        }
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