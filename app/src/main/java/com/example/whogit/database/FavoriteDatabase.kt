package com.example.whogit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whogit.data.model.UserModel

@Database(entities = [UserModel.ItemsItem::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase:RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
}