package com.example.lordofthegames.games

/**
 * ##Achievement di gioco
 * @param name nome dell'achievement
 * @param descr descrizione dell'achievement(può essere vuoto)
 * @param img immagine dell'achievement(può essere vuoto)
 * @param count Conta degli elementi che servono per completare l'achievement (Default value: 1)
 * @param status Se è completato o no(Default value: false)
 */
class Achievement(
    var name: String,
    var descr: String?,
    var img: String?,
    var count: Int = 1,
    var status: Boolean = false
    ) {
}