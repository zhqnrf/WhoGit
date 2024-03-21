package com.example.whogit.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.whogit.data.store.AppreanceSetting
import com.example.whogit.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.whogit.ui.utils.Result

class ViewModel(private val preferences: AppreanceSetting): ViewModel() {

    val resultUser = MutableLiveData<Result>()
    fun getThemeMode() = preferences.getTheme().asLiveData()

    fun getUser(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getSearchUser(mapOf(
                        "q" to username,
                        "per_page" to 100

                    ))
                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect{
                resultUser.value = Result.Success(it.items)
            }
        }
    }

    class Factory(private val preferences: AppreanceSetting):
            ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T  = ViewModel(preferences) as T
            }
}