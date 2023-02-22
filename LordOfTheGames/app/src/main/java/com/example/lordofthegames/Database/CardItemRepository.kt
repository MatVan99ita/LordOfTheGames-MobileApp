package com.example.lordofthegames.Database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.lordofthegames.recyclerView.CardItem

class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        nCardItemRepository(application: Application?) {
    private lateinit var cardItemDAO: CardItemDAO
    val cardItemList: LiveData<List<CardItem?>?>?

    init {
        val db: CardItemDatabase? = application?.let { CardItemDatabase.getDatabase(it) }
        if (db != null) {
            cardItemDAO = db.cardItemDAO()!!
        }
        cardItemList = cardItemDAO.getCardItems()
    }

    fun addCardItem(cardItem: CardItem?) {
        CardItemDatabase.executor.execute(Runnable { cardItemDAO.addCardItem(cardItem) })
    }
}