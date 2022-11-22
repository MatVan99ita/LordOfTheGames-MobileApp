package com.example.lordofthegames.Database

import android.view.SurfaceControl.Transaction
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lordofthegames.recyclerView.CardItem


@Dao
interface CardItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCardItem(cardItem: CardItem?)

    fun getCardItems(): LiveData<List<CardItem?>?>?
}

