package com.example.lordofthegames.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lordofthegames.EntityRepo.UserRepo
import com.example.lordofthegames.db_entities.User
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepo): ViewModel() {

    val allItems = repository.allItems

    fun addItem(item: User) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun deleteItem(item: User) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun clearItems() = viewModelScope.launch {
        repository.deleteAllItems()
    }

}

class UserViewModelFactory(private val repository: UserRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}