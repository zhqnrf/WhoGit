package com.example.whogit.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whogit.database.ConfigDatabase

class FavoriteViewModel(private val databaseConfig: ConfigDatabase):ViewModel() {
    class  factory(private val databaseConfig: ConfigDatabase): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(databaseConfig) as T
    }

    fun getFavoriteUser() = databaseConfig.favoriteDao.loads()
}