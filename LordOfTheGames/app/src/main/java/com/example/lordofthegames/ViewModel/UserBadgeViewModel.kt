package com.example.lordofthegames.ViewModel

import android.app.Application
import com.example.lordofthegames.Database.AbstractViewModel
import com.example.lordofthegames.user_badge.UserBadge

/** TODO: aggiungere in questi fragment per generare notifiche con badge
 *      LoggedInFragment,
 *      HomeFragment,
 *      GameDetailsFragment,
 *      DiscussionSpecificFragment
 * */
class UserBadgeViewModel(application: Application): AbstractViewModel(application) {

    fun getCompletedGameCount(user_ref: String): Int {
        return repository.getCompletedGameCount(user_ref)
    }

    fun getListCount(user_ref: String): Int {
        return 0
    }

    fun getCompletedAchievementCount(user_ref: String): Int {
        return repository.getCompletedAchievementCount(user_ref)
    }

    fun getDiscussionCount(user_ref: String): Int {
        return repository.getDiscussionCount(user_ref)
    }

    fun getCommentCount(user_ref: String): Int {
        return repository.getCommentCount(user_ref)
    }

    fun getLikeCount(user_ref: String): Int{
        return repository.getLikeCount(user_ref)
    }

    fun getAll(user_ref: String): UserBadge {
        return UserBadge(
            game_level = this.getListCount(user_ref),
            played_level = this.getCommentCount(user_ref),
            achievement_level = this.getCompletedAchievementCount(user_ref),
            disccussion_num_level = this.getDiscussionCount(user_ref),
            disccussion_like_level = this.getLikeCount(user_ref),
            comments_num_level = this.getCommentCount(user_ref),
        )
    }

}