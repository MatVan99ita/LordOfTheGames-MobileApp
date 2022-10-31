package com.example.lordofthegames.Games

import android.media.Image

/*
 * (*) -> da scegliere un nome migliore o comunque da modificare
 */

/**
 * ##La classe per identificare i giochi
 * @param Name         Nome del gioco
 * @param Achievements Lista degli obbiettivi
 * @param Image        copertina di gioco
 * @param Categories   Categorie del gioco
 * @param Status       Stato del gioco(wishlist/playing/dropped/finished/to add/need to be played(*))
 * @param Notes        Il blocco note per segnarsi le cose da ricordarsi o quello che si vuole
 */
class Game(
    var name: String,
    var achievements: List<Achievement>,
    var image: Image,
    var categories: List<Categories>,
    var status: String,
    var note: Notes
    ) {

}