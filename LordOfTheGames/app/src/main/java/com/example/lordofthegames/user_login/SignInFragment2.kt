package com.example.lordofthegames.user_login


import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
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
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities.Companion.GALLERY_IMAGE
import com.example.lordofthegames.Utilities.Companion.REQUEST_CAMERA_PERMISSION
import com.example.lordofthegames.Utilities.Companion.REQUEST_GALLERY_PERMISSION
import com.example.lordofthegames.Utilities.Companion.REQUEST_IMAGE_CAPTURE
import com.example.lordofthegames.Utilities.Companion.createImageFile
import com.example.lordofthegames.db_entities.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
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

    private lateinit var photoFile: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin_img, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fragmentContainerView = requireActivity().findViewById(R.id.fragment_container_view)

         val btn_img: Button = requireView().findViewById(R.id.fottinn)
         val imgview: ImageView = requireView().findViewById(R.id.fottimi)
         var capturedImageUri = mutableSetOf<Uri>(Uri.EMPTY)

        btn_img.setOnClickListener {
            val myAlertDialog = MaterialAlertDialogBuilder(
                requireContext()
            )
            myAlertDialog.setTitle("Upload Pictures Option")
            myAlertDialog.setMessage("How do you want to set your picture?")
            myAlertDialog.setPositiveButton(
                "Gallery"
            ) { _, _ ->
                // Controlla e richiedi i permessi per accedere alla galleria
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //ActivityResultLauncher()
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_GALLERY_PERMISSION
                    )
                } else {
                    openGallery()
                }
            }

            myAlertDialog.setNegativeButton(
                "Camera"
            ) { _, _ ->
                // Controlla e richiedi i permessi per accedere alla fotocamera
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                } else {
                    openCamera()
                }
            }
            myAlertDialog.show()
        }

        super.onViewCreated(view, savedInstanceState)
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

    fun addUser(user: User) {
        //repository.insertUser(user)
    }

    fun isValidPassword(password: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
        return password.matches(regex)
    }

    fun isValidMail(email: String): Boolean {
        // Controlla se la password ha almeno 6 caratteri e contiene almeno una lettera minuscola,
        // una lettera maiuscola, un numero e un carattere speciale.
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matches(email)
    }

    fun signin(nickn: String, passw: String, c_passw: String, email: String) {

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
        parentFragmentManager.beginTransaction().replace(R.id.signin_fragment, SignInFragment2()).addToBackStack(null).commit()

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

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXExample"
    }

    private fun openGallery() {
        val photo = Intent()
        val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GALLERY_IMAGE)
        val chooserIntent = Intent.createChooser(photo, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(photoPickerIntent))

        startActivityForResult(chooserIntent, GALLERY_IMAGE)

    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("Errore nella fotocamera", ex.toString())
            }
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.android.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                Log.e("IMMAGGINA PUAOI", "IMMAGGINA PUAOI")
            }
        }
    }


}