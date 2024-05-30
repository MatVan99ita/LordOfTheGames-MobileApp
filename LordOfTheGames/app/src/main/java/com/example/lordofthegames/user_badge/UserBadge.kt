package com.example.lordofthegames.user_badge

class UserBadge {
    /* TODO: aggiungere tipo una lista con gli chievement dell'utente come
         giochi completati,
         achievement completati,
         giochi aggiunti alle singole liste
         giochi abbandonati
         _
         ALLA BATTLE CATS a livelli
         *
         legno x10 ->
         ferro x20 ->
         rame x35 ->
         bronzo   V IV III II I -> x40   - 70
         argento  V IV III II I -> x75   - 99
         oro      V IV III II I -> x100  - 700
         diamante V IV III II I -> x1000 - 1300
         smeraldo V IV III II I -> x1400 - 1600
         platino  V IV III II I -> x1700 - 1800
         immortal V IV III II I -> x2000 - e oltre
*/
    private var game_level: Int = 0
    private var achievement_level: Int = 0
    private var abandoned_level: Int = 0
    private var played_level: Int = 0
    private var account_created: Boolean = false

    private enum class badges{
        WOOD,

        IRON,

        COPPER,

        BRONZE5,
        BRONZE4,
        BRONZE3,
        BRONZE2,
        BRONZE1,

        SILVER4,
        SILVER3,
        SILVER2,
        SILVER1,

        GOLD3,
        GOLD2,
        GOLD1,

        DIAMOND2,
        DIAMOND1,

        PLATINUM,
        
        FATTI_UNA_VITA,
    }

}