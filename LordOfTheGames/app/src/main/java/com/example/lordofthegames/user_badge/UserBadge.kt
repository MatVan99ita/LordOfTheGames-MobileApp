package com.example.lordofthegames.user_badge


/**~ achievement utenti
     {
         ~ giochi totali
         ~ giochi completati
         ~ achievement completati
         ~ Tot discussioni fatte
         ~ Tot commenti scritti
         ~ Tot like ricevuti
     }
     legno     -> x10
     ferro     -> x20
     rame      -> x35
     argento   -> x75
     oro       -> x100
     diamante  -> x200
     smeraldo  -> x300
     platino   -> x600
     immortal1 -> x800
     immortal2 -> x1000
     _
*/
data class UserBadge (
    var game_level: Int = 0,
    var achievement_level: Int = 0,
    var played_level: Int = 0,
    var disccussion_num_level: Int = 0,
    var disccussion_like_level: Int = 0,
    var comments_num_level: Int = 0,
    )