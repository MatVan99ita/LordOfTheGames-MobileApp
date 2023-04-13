package com.example.lordofthegames.ViewModel


import android.app.Application
import android.graphics.Bitmap
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
// import com.example.lordofthegames.Database.LOTGRepository
import com.example.lordofthegames.R
import com.example.lordofthegames.Utilities
// import com.example.lordofthegames.db_entities.Game
import kotlinx.coroutines.launch

class WordViewModel(private val repository: String/*LOTGRepository*/){// : ViewModel() {
/*
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGames: LiveData<List<Game>> = repository.allGames
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }
}

class WordViewModelFactory(private val repository: LOTGRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AddViewModel(application: Application, val repo: LOTGRepository) : AndroidViewModel(application){
    var imageBitmap = MutableLiveData<Bitmap?>()
    private var app: Application
    private var cardItems: LiveData<List<Game>>



    init {
        this.app = application
        initializeBitmap()
        cardItems = repo.allGames
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

*/
}