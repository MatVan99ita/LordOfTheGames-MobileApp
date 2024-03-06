package com.example.lordofthegames.recyclerView

import okhttp3.internal.toHexString

class PlatformCardItem(var platFormName: String, var color: Int) {
    override fun toString(): String {
        return "$platFormName - ${color.toLong()} - ${color.toLong().toHexString()}"
    }
}