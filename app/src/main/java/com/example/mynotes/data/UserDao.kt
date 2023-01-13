package com.example.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao

@Dao
interface UserDao : CoreDao<Note> {

    @Query("DELETE FROM Note")
    suspend fun deleteAll()

    @Query("SELECT * FROM Note WHERE idRoom = 1 ")
    fun getUser() : LiveData<Note?>

    @Query("SELECT EXISTS (SELECT 1 FROM Note WHERE idRoom = 1)")
    suspend fun isLogin(): Boolean
}