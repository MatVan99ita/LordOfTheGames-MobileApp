package com.example.lordofthegames.Community

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentAddDiscussionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.nio.charset.StandardCharsets
import java.util.Arrays
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DiscussionCreateFragment: Fragment() {


    private lateinit var bind: FragmentAddDiscussionBinding
    private lateinit var viewm: DiscussionViewModel
    private var isAllFabsVisible = false
    private lateinit var cameraExecutor: ExecutorService
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

        bind.btnSavePost.setOnClickListener { this.createDiscussion() }
        bind.btnNopePost.setOnClickListener { this.deleteDiscussion() }
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

        //TODO: sistemare posizione e date con calendario
        //      aggiungere le cose di posizione e date per creare discussioni eventp e metterle anche poi nella vista di creazione
        val disc_id = viewm.saveNewDiscussion(
            title = bind.etTitle.text?.toString()!!,
            content = bind.etContent.text?.toString()!!,
            usr = s,
            game = game_id,
            img = if(c_img != null) c_img else null,
            location = "",
            dataInizio = "",
            dataFine = ""
        )



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


}