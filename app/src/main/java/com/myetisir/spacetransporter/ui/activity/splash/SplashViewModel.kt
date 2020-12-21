package com.myetisir.spacetransporter.ui.activity.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.repository.MainRepository
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    private val _open = MutableLiveData(false)
    val open: LiveData<Boolean>
        get() = _open

    fun startOpenTimer() {
        viewModelScope.launch {
            delay(1000)
            _open.postValue(true)
        }
    }
}