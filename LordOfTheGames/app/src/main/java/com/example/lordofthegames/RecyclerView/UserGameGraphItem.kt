package com.example.lordofthegames.recyclerView

class UserGameGraphItem(
    val gameNumTot: Int,
    val abandonedTot: Int,
    val completedTot:Int,
    val playingTot:Int,
    val planToPlayTot:Int,
) {

    override fun toString(): String {
        return "    UserGameGraphItem { \n" +
                "   gameNumTot = $gameNumTot \n" +
                "   abandonedTot = $abandonedTot\n" +
                "   completedTot = $completedTot\n" +
                "   playingTot = $playingTot\n" +
                "   planToPlayTot = $planToPlayTot\n" +
                "}"
    }
}