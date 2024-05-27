package com.example.lordofthegames.user_login


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.GameDetails.GameDetActivity
import com.example.lordofthegames.MainActivity
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.Utilities.Companion.CAMERA_PERMISSION_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.CAMERA_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.GALLERY_PERMISSION_REQUEST_CODE
import com.example.lordofthegames.Utilities.Companion.GALLERY_REQUEST_CODE
import com.example.lordofthegames.db_entities.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import kotlin.math.sqrt


class SignInFragment2: Fragment(){

    private lateinit var nick: String
    private lateinit var mail: String
    private lateinit var password: String

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var currentPhotoPath: String

    private lateinit var imageView: ImageView

    private lateinit var photoFile: Uri

    private lateinit var btnImg2: Button


    private var mScaleGestureDetector: ScaleGestureDetector? = null

    private var oldDist = 1f
    private val MIN_SCALE = 0.5f
    private val MAX_SCALE = 2f
    private val MIN_SIZE = 100f
    private val MAX_SIZE = 400f
    private var mScaleFactor = 1.0f
    private var mPosX = 0f
    private var mPosY = 0f
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f


    private lateinit var loggedViewModel: LoggedViewModel




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loggedViewModel = ViewModelProvider(requireActivity())[LoggedViewModel::class.java]
        return inflater.inflate(R.layout.fragment_signin_img, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mScaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())

        fragmentContainerView = requireActivity().findViewById(R.id.fragment_container_view)

        val btnImg: Button = requireView().findViewById(R.id.fottinn)

        btnImg2 = requireView().findViewById(R.id.fottinn2)
        imageView = requireView().findViewById(R.id.fottimi)


        imageView.setOnTouchListener { _, event ->
            mScaleGestureDetector!!.onTouchEvent(event)
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
        } // */
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


    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }

    private fun limitTranslation(m: Matrix) {
        val values = FloatArray(9)
        m.getValues(values)
        val transX = values[Matrix.MTRANS_X]
        val transY = values[Matrix.MTRANS_Y]
        val scaleX = values[Matrix.MSCALE_X]
        val scaleY = values[Matrix.MSCALE_Y]
        if (transX > 0) values[Matrix.MTRANS_X] = 0f
        if (transY > 0) values[Matrix.MTRANS_Y] = 0f
        if (transX < -(imageView.width * (scaleX - 1))) values[Matrix.MTRANS_X] =
            -(imageView.width * (scaleX - 1))
        if (transY < -(imageView.height * (scaleY - 1))) values[Matrix.MTRANS_Y] =
            -(imageView.height * (scaleY - 1))
        m.setValues(values)
    }

    private fun limitScaling(m: Matrix) {
        val values = FloatArray(9)
        m.getValues(values)
        val scaleX = values[Matrix.MSCALE_X]
        val scaleY = values[Matrix.MSCALE_Y]
        if (scaleX < MIN_SCALE) values[Matrix.MSCALE_X] = MIN_SCALE
        if (scaleY < MIN_SCALE) values[Matrix.MSCALE_Y] = MIN_SCALE
        if (scaleX > MAX_SCALE) values[Matrix.MSCALE_X] = MAX_SCALE
        if (scaleY > MAX_SCALE) values[Matrix.MSCALE_Y] = MAX_SCALE
        val scaledWidth: Float =
            imageView.drawable.intrinsicWidth * values[Matrix.MSCALE_X]
        val scaledHeight: Float =
            imageView.drawable.intrinsicHeight * values[Matrix.MSCALE_Y]
        if (scaledWidth < MIN_SIZE) values[Matrix.MSCALE_X] =
            MIN_SIZE / imageView.drawable.intrinsicWidth
        if (scaledHeight < MIN_SIZE) values[Matrix.MSCALE_Y] =
            MIN_SIZE / imageView.drawable.intrinsicHeight
        if (scaledWidth > MAX_SIZE) values[Matrix.MSCALE_X] =
            MAX_SIZE / imageView.drawable.intrinsicWidth
        if (scaledHeight > MAX_SIZE) values[Matrix.MSCALE_Y] =
            MAX_SIZE / imageView.drawable.intrinsicHeight
        m.setValues(values)
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


    fun signin(nickn: String, passw: String, email: String, img: Bitmap) {

        val sp: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val res = loggedViewModel.insertNewUsr(User(mail, nick, passw, Utilities.convertBitmapToByteArray(img)))
        if(res > 0){
            sp.edit().putString("logged", "logged").putString("nickname", nickn).putString("email", mail).apply()
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_view, LoggedInFragment()).addToBackStack(null).commit()
        }
        startActivity(
            Intent(
                requireActivity(),
                MainActivity::class.java
            )
        )
    }


    fun saveImage(
        contentResolver: ContentResolver,
        capturedImageBitmap: Bitmap?
    ) {

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
        //val drawable: Drawable? = ContextCompat.getDrawable(requireContext(),
        //    this.resources.getIdentifier("yo_listen_foreground", "mipmap", ""))
        var img: Bitmap? = null

        if (resultCode == Activity.RESULT_OK) {
            btnImg2.visibility = View.VISIBLE
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    // L'immagine è stata catturata con successo dalla fotocamera
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    img = imageBitmap
                    // Fai qualcosa con l'immagine (es. mostrala in un'ImageView)
                    imageView.setImageBitmap(imageBitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    // L'immagine è stata selezionata dalla galleria
                    val selectedImageBitmap = data?.data //.extras?.get("data") as Bitmap
                    // Fai qualcosa con l'URI dell'immagine (es. caricala in un'ImageView)
                    img = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageBitmap)

                    imageView.setImageURI(selectedImageBitmap)
                }
            }

            btnImg2.setOnClickListener {
                val bundle: Bundle? = arguments

                // Now you have your ArrayList<String>
                if (bundle != null && img != null) {
                    this.nick = bundle.getString("nick", "Gabibbo")
                    this.password = bundle.getString("passw", "Gabibbo")
                    this.mail = bundle.getString("mail", "Gabibbo")
                    Log.i("MAMMETA", "$nick $password $mail")
                    signin(nick, password, mail, img)
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

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        // when a scale gesture is detected, use it to resize the image
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            imageView.scaleX = mScaleFactor
            imageView.scaleY = mScaleFactor
            return true
        }
    }


}
