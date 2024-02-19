package com.example.lordofthegames.recyclerView

class GameCardItem(var imageResource: String, var gameTitle: String, var gameId: Int){
    override fun toString(): String {
        return "  $imageResource - $gameTitle  "
    }
}
