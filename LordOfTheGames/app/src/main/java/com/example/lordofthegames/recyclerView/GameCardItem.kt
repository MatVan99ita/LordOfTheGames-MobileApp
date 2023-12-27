package com.example.lordofthegames.recyclerView

class GameCardItem(var imageResource: String, var gameTitle: String){
    override fun toString(): String {
        return "  $imageResource - $gameTitle  "
    }
}
