package com.example.whogit.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.whogit.data.model.UserModel
import com.example.whogit.data.retrofit.ApiConfig
import com.example.whogit.database.ConfigDatabase
import com.example.whogit.ui.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val database: ConfigDatabase) : ViewModel() {

    val userInDatabase: LiveData<Boolean> = MutableLiveData()
    val DeleteFav = MutableLiveData<Boolean>()
    val isSuccessAddFav = MutableLiveData<Boolean>()
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()

    private var isFavorite = false

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig.gitHubService.getDetailUser(username)
                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch { exception ->
                Log.e("Error", exception.message.toString())
                exception.printStackTrace()
                resultDetailUser.value = Result.Error(exception)
            }.collect { result ->
                resultDetailUser.value = Result.Success(result)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig.gitHubService.getFollowers(username)
                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch { exception ->
                Log.e("Error", exception.message.toString())
                exception.printStackTrace()
                resultFollowers.value = Result.Error(exception)
            }.collect { result ->
                resultFollowers.value = Result.Success(result)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig.gitHubService.getFollowing(username)
                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch { exception ->
                Log.e("Error", exception.message.toString())
                exception.printStackTrace()
                resultFollowing.value = Result.Success(exception)
            }.collect { result ->
                resultFollowing.value = Result.Success(result)
            }
        }
    }

    fun setButtonFav(item: UserModel.ItemsItem?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    DeleteFav.value = false
                    database.favoriteDao.delete(item)
                } else {
                    isSuccessAddFav.value = true
                    database.favoriteDao.insert(item)
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun checkFavorite(id: Int, listenFav: () -> Unit) {
        viewModelScope.launch {
            val favoriteUser = database.favoriteDao.findById(id)
            if (favoriteUser == null) {
                listenFav()
                isFavorite = true
            }
        }
    }

    fun checkingUser(userId: Int) {
        viewModelScope.launch {
            val count = database.favoriteDao.userExist(userId)
            (userInDatabase as MutableLiveData).postValue(count > 0)
        }
    }

    class Factory(private val db: ConfigDatabase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}
