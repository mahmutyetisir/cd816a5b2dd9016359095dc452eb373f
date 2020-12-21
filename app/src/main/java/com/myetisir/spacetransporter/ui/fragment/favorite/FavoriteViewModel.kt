package com.myetisir.spacetransporter.ui.fragment.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.manager.TransportManager
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.repository.StationRepository
import com.myetisir.spacetransporter.util.Resource
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel @ViewModelInject constructor(
    private val stationRepository: StationRepository,
    private val transportManager: TransportManager
) : BaseViewModel() {

    private val _favoriteStations = MutableLiveData<Resource<List<Station>>>()
    val favoriteStations: LiveData<Resource<List<Station>>>
        get() = _favoriteStations

    fun getFavoriteStations() {
        viewModelScope.launch(Dispatchers.IO) {
            stationRepository.getStations().collect {
                if (it is Resource.Success) {
                    withContext(Dispatchers.Main) {
                        val world = Station("Dünya", 0.0, 0.0, 0, 0, 0)
                        it.data.forEach { station ->
                            station.eus = transportManager.calculateNeedEUS(world, station)
                        }
                        _favoriteStations.value = it
                    }
                }
            }
        }
    }

    fun removeFavoriteStation(station: Station) {
        viewModelScope.launch(Dispatchers.IO) {
            stationRepository.deleteStation(station.name)
        }
    }
}