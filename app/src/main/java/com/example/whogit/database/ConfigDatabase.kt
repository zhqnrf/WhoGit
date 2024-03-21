package com.example.whogit.database

import android.content.Context
import androidx.room.Room

class ConfigDatabase(private val context: Context) {

    private val database = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java,
        "Favorite.db"
    )
        .allowMainThreadQueries() 
        .build()

    val favoriteDao = database.favoriteDao()
}
