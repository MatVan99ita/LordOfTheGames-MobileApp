package com.example.lordofthegames.games

/*
 * (*) -> da scegliere un nome migliore o comunque da modificare
 */

/**
 * ##La classe per identificare i giochi
 * @param name         Nome del gioco
 * @param image        link della copertina di gioco
 * @param status       Stato del gioco(wishlist/playing/dropped/finished/to add/need to be played(*))
 * @param achievements Lista degli obbiettivi
 * @param categories   Categorie del gioco
 * @param note         Il blocco note per segnarsi le cose da ricordarsi o quello che si vuole
 */
class Game(
    var name: String,
    var achievements: List<Achievement>,
    var image: String,
    var categories: List<Categories>,
    var status: String,
    var note: Notes
    ) {

}