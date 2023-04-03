package com.example.githubuser.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoritUser: FavoriteUser)

    @Query("SELECT * FROM Favorite_User")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM Favorite_User WHERE Favorite_User.id = :id ")
    suspend fun checkUser(id : Int): Int

    @Query("DELETE FROM Favorite_User WHERE Favorite_User.id = :id ")
    suspend fun removeFavorite(id : Int): Int

}