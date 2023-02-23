package com.example.lordofthegames.ViewModel


import android.app.Application
import android.graphics.Bitmap
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.db_entities.Game


class AddViewModel(application: Application) : AndroidViewModel(
    application
) {
    var imageBitmap = MutableLiveData<Bitmap?>()
    private var app: Application
    private var cardItems: LiveData<List<Game>>

    init {
        this.app = application
        initializeBitmap()
        val repository = LOTGRepository(application)
        cardItems = repository.getGame()
    }

    fun setImageBitmap(bitmap: Bitmap){
        this.imageBitmap.value = bitmap
    }

    fun initializeBitmap() {
        val drawable = ResourcesCompat.getDrawable( app.resources, R.drawable.ic_yeee_background, app.theme )
        val bitmap: Bitmap? = drawable?.let { Utilities.drawableToBitmap(it) }
        imageBitmap.value = bitmap
    }

    fun getRobeh(): LiveData<List<Game>> {
        return cardItems
    }


}
