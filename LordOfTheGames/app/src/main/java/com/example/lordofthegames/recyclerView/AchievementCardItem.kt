package com.example.lordofthegames.recyclerView

class AchievementCardItem(
    var achieve_id: Int,
    var img: String,
    var name: String,
    var descr: String,
    var actual_count: Int,
    var total_count: Int,
    var completed: Int = 0,
)