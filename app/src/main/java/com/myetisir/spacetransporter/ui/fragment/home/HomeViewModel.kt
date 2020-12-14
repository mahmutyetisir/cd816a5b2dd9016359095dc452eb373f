package com.myetisir.spacetransporter.ui.fragment.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.model.Transporter
import com.myetisir.spacetransporter.data.repository.TransporterRepository
import com.myetisir.spacetransporter.util.Resource
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val transporterRepository: TransporterRepository) :
    BaseViewModel() {

    private val _transporter = MutableLiveData<Resource<Transporter>>()
    val transporter: LiveData<Resource<Transporter>>
        get() = _transporter


    fun getTransporter() {
        CoroutineScope(Dispatchers.IO).launch {
            transporterRepository.getTransporter().collect {
                viewModelScope.launch {
                    _transporter.value = it
                }
            }
        }
    }
}