package com.example.whogit.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.whogit.data.store.AppreanceSetting
import kotlinx.coroutines.launch

class ViewModelSetting(private val pref : AppreanceSetting): ViewModel() {

    class Factory(private val pref: AppreanceSetting):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T  = ViewModelSetting(pref) as T
    }

    fun getTheme() = pref.getTheme().asLiveData()

    fun saveTheme(isNightMode: Boolean){
        viewModelScope.launch {
            pref.getSaveTheme(isNightMode)
        }
    }
}