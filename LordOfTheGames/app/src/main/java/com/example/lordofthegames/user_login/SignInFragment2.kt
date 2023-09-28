package com.example.lordofthegames.user_login


import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities.Companion.CAMERA_AND_GALLERY_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.CAMERA_PERMISSION_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.CAMERA_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.GALLERY_PERMISSION_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.GALLERY_REQUEST_CODE
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException
import java.lang.Math.sqrt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService


class SignInFragment2: Fragment() {

    private lateinit var nick: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var reqpassword: TextInputEditText
    private lateinit var lbl_error: TextView

    private lateinit var imageCapture: ImageCapture

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var currentPhotoPath: String

    private lateinit var imageView: ImageView

    private lateinit var photoFile: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin_img, container, false)
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var scaleFactor = 1f
        var previousX = 0f
        var previousY = 0f

        fragmentContainerView = requireActivity().findViewById(R.id.fragment_container_view)

         val btnImg: Button = requireView().findViewById(R.id.fottinn)
         imageView = requireView().findViewById(R.id.fottimi)

        imageView.setOnTouchListener { _, event ->

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    previousX = event.x
                    previousY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - previousX
                    val deltaY = event.y - previousY

                    imageView.translationX += deltaX
                    imageView.translationY += deltaY

                    previousX = event.x
                    previousY = event.y
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val distance = getDistance(event)
                    scaleFactor = distance / imageView.width
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    scaleFactor = 1f
                }
                MotionEvent.ACTION_UP -> {
                    // Gestisci il click sull'immagine, se necessario
                }
            }

            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor

            true
        }


         btnImg.setOnClickListener {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                1
            )



            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
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
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    ),
                    1
                )
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

    }



    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        //this.image.setBackground(Drawable.createFromPath(currentPhotoPath));
        return image
    }

    private fun getDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return kotlin.math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    fun signin(nickn: String, passw: String, c_passw: String, email: String, img: Bitmap) {

        /**TODO: Riattivare questi una volta sistemata la foto
         *if(!isValidMail(email)){
         *    lbl_error.error = "Mail is required. Must be name@domain.net"
         *    lbl_error.requestFocus()
         *} else if(nickn.length < 6){
         *    lbl_error.error = "Nickname must be 6(or more) character ";
         *    lbl_error.requestFocus();
         *    return;
         *}
         *
         *if(!isValidPassword(passw)) {
         *    lbl_error.error = "Password is required. Must be 6 character. Must have a special character, a number and a Uppercase chapter(Ex.:Banana33!)";
         *    lbl_error.requestFocus();
         *    return;
         *} else if(c_passw != passw){
         *    lbl_error.error = "Password must be the same";
         *    lbl_error.requestFocus();
         *}
         */

        Log.w("SIGNIN", "$nickn $email $passw")
        /**
         *
         *     TODO: aggiungere i dati al db e il log nelle shared pref
         *              val salt = Utilities.generateSalt()
         *              val hashedPassword = Utilities.hashPassword(passw, salt)
         *              val check = 10// repository.insertUser(User(email, nickn, passw))
         *              if(check > 0){
         *                  val sharedPrefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
         *                  val editor = sharedPrefs.edit()
         *                  editor.putString("logged", "")
         *                  editor.putString("mail", email)
         *                  editor.putString("nick", nickn)
         *                  editor.apply()
         *                  //parentFragmentManager.beginTransaction().replace(R.id.login_fragment, LoggedInFragment()).addToBackStack(null).commit()
         *              }
         */

    }


    fun saveImage(
        contentResolver: ContentResolver,
        capturedImageBitmap: Bitmap?
    ) {
        /***
         * TODO: Aggiungere il salvataggio dell'immagine ache nel db
         */

        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${SystemClock.uptimeMillis()}")

        val imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val outputStream = imageUri?.let { contentResolver.openOutputStream(it) }
        capturedImageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream?.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXExample"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
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
            GALLERY_PERMISSION_REQUEST_CODE -> {
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
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    // L'immagine è stata catturata con successo dalla fotocamera
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    // Fai qualcosa con l'immagine (es. mostrala in un'ImageView)
                    imageView.setImageBitmap(imageBitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    // L'immagine è stata selezionata dalla galleria
                    val selectedImageBitmap = data?.data //.extras?.get("data") as Bitmap
                    // Fai qualcosa con l'URI dell'immagine (es. caricala in un'ImageView)
                    imageView.setImageURI(selectedImageBitmap)
                }
            }
        }
    }


    private fun getBitmap(selectedPhotoUri: Uri, contentResolver: ContentResolver): Bitmap {
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

    fun Context.createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        return File.createTempFile(
            imageFileName,
            ".jpg",
            externalCacheDir
        )
    }

}