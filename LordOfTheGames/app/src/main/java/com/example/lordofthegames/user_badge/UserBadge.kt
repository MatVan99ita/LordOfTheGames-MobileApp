package com.example.lordofthegames.user_badge

class UserBadge {
    /* TODO: capito come gestire la gameification
         • imageview con tipo la medaglia del rank rispetto alla classifica(?) e di fianco mega textcview con la posizione => fare query con  COALESCE
         • contatori vari:
            ~ achievement completati
            {
                {
                    ~ media achievement completati
                    ~ percentuale di completamento media dei giochi
                    # N.B. Queste 2 sono uguali
                }
                # N.B.: Questi avranno gli achievement dell'utemte
            }
            ~ Tot discussioni fatte
            ~ Tot commenti scritti
            {
                ~ Tot like ricevuti
                ~ Tot dislike ricevuti
                #N.B: Specifico delle discussioni
            }
         _
         ALLA BATTLE CATS a livelli <- pensare a come ridurli tanto non ne servono così tanti per la presentazione
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
         _
         Mettere anche una mini view in cui si spiegano i vari obiettivi e si fanno vedere le varie medagli
         N.B.: IMMAGINI CREATE ORA TOCCA INSERIRLE
*/ //POTREI FARLA COME UNA DB ENTITIES E L'ENUM SERVE A TRADURRE IL LIVELLO CHE FA +1 AD OGNI COSA CHE ACCADE
    private var game_level: Int = 0
    private var achievement_level: Int = 0
    private var abandoned_level: Int = 0
    private var played_level: Int = 0

    private var account_created: Boolean = false //<- BRONZO DIRETTO e non so se serve davvero

    private var disccussion_num_level: Int = 0
    private var disccussion_like_level: Int = 0
    private var disccussion_dislike_level: Int = 0

    private var comments_num_level: Int = 0
    private var comments_like_level: Int = 0
    private var comments_dislike_level: Int = 0

    private enum class badges{
        WOOD,
        //x10 ->,
        IRON ,
        //x20 ->,
        COPPER ,
        //x35 ->,
        BRONZEV,
        BRONZEIV,
        BRONZEIII,
        BRONZEII,
        BRONZEI ,
        //-> x40   - 70,
        SILVERV,
        SILVERIV,
        SILVERIII,
        SILVERII,
        SILVERI ,
        //-> x75   - 99,
        GOLDV,
        GOLDIV,
        GOLDIII,
        GOLDII,
        GOLDI,
        //-> x100  - 700,
        DIAMONDV,
        DIAMONDIV,
        DIAMONDIII,
        DIAMONDII,
        DIAMONDI,
        //-> x1000 - 1300,
        EMERALDV,
        EMERALDIV,
        EMERALDIII,
        EMERALDII,
        EMERALDI,
        //-> x1400 - 1600,
        PLATINUMV,
        PLATINUMIV,
        PLATINUMIII,
        PLATINUMII,
        PLATINUMI,
        //-> x1700 - 1800,
        IMMORALV,
        IMMORALIV,
        IMMORALIII,
        IMMORALII,
        IMMORALI,
        //-> x2000 - e oltre,
        FATTI_UNA_VITA,
    }

}