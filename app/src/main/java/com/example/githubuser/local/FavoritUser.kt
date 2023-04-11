package com.example.githubuser.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Favorite_User")
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String,
) : Serializable
