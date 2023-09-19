package com.example.lordofthegames.EntityDAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lordofthegames.db_entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();
}
