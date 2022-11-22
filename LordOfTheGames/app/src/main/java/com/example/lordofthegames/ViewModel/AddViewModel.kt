package com.example.lordofthegames.ViewModel


import android.app.Application
import android.graphics.Bitmap
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities


class AddViewModel(application: Application) : AndroidViewModel(
    application
) {
    public var imageBitmap = MutableLiveData<Bitmap>()
    private var app: Application
    init {
        this.app = application
        initializeBitmap()
    }

    public fun setImageBitmap(bitmap: Bitmap){
        this.imageBitmap.value = bitmap
    }

    public fun initializeBitmap() {
        val drawable = ResourcesCompat.getDrawable( app.resources, R.drawable.ic_yeee_background, app.theme )
        val bitmap: Bitmap? = drawable?.let { Utilities.drawableToBitmap(it) }
        imageBitmap.value = bitmap
    }


}
