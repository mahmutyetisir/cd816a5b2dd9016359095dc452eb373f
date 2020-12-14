package com.myetisir.spacetransporter.ui.activity.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.repository.MainRepository
import com.myetisir.spacetransporter.util.Resource
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    private val _stations = MutableLiveData<Resource<List<Station>>>()
    val stations: LiveData<Resource<List<Station>>>
        get() = _stations

    fun getStations() {
        viewModelScope.launch {
            mainRepository.getStation().collect {
                _stations.value = it
            }
        }
    }
}