package com.example.lordofthegames.home

import com.example.lordofthegames.db_entities.Game
import com.example.lordofthegames.db_entities.GameCategory
import com.example.lordofthegames.db_entities.GamePlatform
import com.example.lordofthegames.recyclerView.CategoryCardItem
import com.example.lordofthegames.recyclerView.GameCardItem
import com.example.lordofthegames.recyclerView.PlatformCardItem

data class GameItem (
    val game: GameCardItem,
    val platform: MutableList<PlatformCardItem>,
    val category: MutableList<CategoryCardItem>
)