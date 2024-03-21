package com.example.whogit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.whogit.data.model.UserModel


@Dao
interface FavoriteDao {


    @Query("SELECT * FROM User")
    fun loads(): LiveData<MutableList<UserModel.ItemsItem>>

    @Query("SELECT * FROM User Where id LIKE :id LIMIT 1")
    fun findById(id: Int): UserModel.ItemsItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserModel.ItemsItem)

    @Query("SELECT COUNT(*) FROM User Where id = :id")
    fun userExist(id: Int): Int

    @Delete
    fun delete(user: UserModel.ItemsItem)

}