package com.myetisir.spacetransporter.ui.activity.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.manager.TransportManager
import com.myetisir.spacetransporter.data.model.*
import com.myetisir.spacetransporter.data.repository.MainRepository
import com.myetisir.spacetransporter.data.repository.StationRepository
import com.myetisir.spacetransporter.data.repository.TransporterRepository
import com.myetisir.spacetransporter.util.Resource
import com.myetisir.spacetransporter.viewmodel.SingleLiveEvent
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val transporterRepository: TransporterRepository,
    private val transportManager: TransportManager,
    private val stationRepository: StationRepository
) :
    BaseViewModel() {

    private val _stations = MutableLiveData<Resource<List<Station>>>()
    val stations: LiveData<Resource<List<Station>>>
        get() = _stations

    fun getStations() {
        viewModelScope.launch {
            if (_stations.value == null || _stations.value is Resource.Error) {
                mainRepository.getStation().collect {
                    _stations.value = it
                    if (it is Resource.Success<List<Station>>) {
                        getTransporter()
                        updateCurrentStation(it.data[0])
                    }
                }
            }
        }
    }

    private fun updateStations(station: Station) {
        val stations = _stations.value
        if (stations != null && stations is Resource.Success) {
            stations.data.firstOrNull { it.name == station.name }?.need = station.need
            _stations.postValue(stations)
        }
    }


    private val _transporter = MutableLiveData<Resource<Transporter>>()
    val transporter: LiveData<Resource<Transporter>>
        get() = _transporter

    private fun getTransporter() {
        if (_transporter.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                transporterRepository.getTransporter().collect {
                    if (it is Resource.Success) {
                        withContext(Dispatchers.Main) {
                            _transporter.value = it
                            crashTotalTime = it.data.durability / 1000
                            startCrashTimer()
                        }
                    }
                }
            }
        }
    }

    private fun updateTransporter(transporter: Transporter) {
        _transporter.postValue(Resource.Success(transporter))
    }


    private val _crash = MutableLiveData(100)
    val crash: LiveData<Int>
        get() = _crash

    private fun updateCrashValue() {
        if (_crash.value != null && _crash.value != 0) {
            _crash.postValue(_crash.value?.minus(10))
        } else if (_crash.value != null && _crash.value == 0) {
            stopCrashTimer()
        }
    }


    private var crashTotalTime: Int? = null
    private val _crashTimer = MutableLiveData<Int>()
    val crashTimer: LiveData<Int>
        get() = _crashTimer

    private var crashTimerJob: Job? = null

    private fun startCrashTimer() {
        crashTimerJob = viewModelScope.launch(Dispatchers.IO) {
            crashTotalTime?.let {
                var time = it
                while (true) {
                    if (time == 0) {
                        time = it
                        updateCrashValue()
                    }
                    delay(1000)
                    --time
                    _crashTimer.postValue(time)
                }
            }
        }
    }

    fun stopCrashTimer() {
        crashTimerJob?.cancel()
    }


    private val _currentStation = MutableLiveData<Station>()
    val currentStation: LiveData<Station>
        get() = _currentStation

    private fun updateCurrentStation(station: Station) {
        _currentStation.postValue(station)
        calculateStationsEUS(station)
    }

    private fun updateCurrentStationToWorld() {
        val stations = _stations.value
        if (stations is Resource.Success) {
            stations.data.firstOrNull { station -> station.name == "Dünya" }?.let {
                updateCurrentStation(it)
            }
        }
    }


    private val _transportMission = SingleLiveEvent<TravelStatus<TravelMission>>()
    val transportMission: LiveData<TravelStatus<TravelMission>>
        get() = _transportMission

    fun startTransport(destinationStation: Station) {
        viewModelScope.launch {
            val transporter = _transporter.value
            val currentStation = _currentStation.value
            val crash = _crash.value
            val currentTransportMission = _transportMission.value

            if (transporter is Resource.Success &&
                currentStation != null &&
                crash != null &&
                (currentTransportMission == null || currentTransportMission is TravelStatus.Error || currentTransportMission is TravelStatus.Success)
            ) {
                val mission = TravelMission(
                    transporter.data,
                    crash,
                    currentStation,
                    destinationStation
                )

                transportManager.startTransport(mission).collect {
                    if (_crash.value != null && _crash.value == 0) {
                        _transportMission.postValue(TravelStatus.Error(TravelError.CrashError))
                    } else {
                        if (it is TravelStatus.Success) {
                            updateTransporter(it.data.transporter)
                            updateCurrentStation(it.data.destinationStation)
                            if (checkMissionComplated()) {
                                _transportMission.postValue(TravelStatus.Complated())
                                return@collect
                            }
                        } else if (it is TravelStatus.Error) {
                            _transportMission.postValue(it)
                            updateCurrentStationToWorld()
                        }
                    }
                }
            }
        }
    }

    private fun calculateStationsEUS(currentStation: Station) {
        if (stations.value is Resource.Success) {
            val tempStations = ArrayList<Station>()
            (stations.value as Resource.Success<List<Station>>).data.forEach { station ->
                if (currentStation.name == station.name) {
                    station.need = currentStation.need
                    station.isCurrentStation = true
                }
                station.eus = transportManager.calculateNeedEUS(currentStation, station)
                tempStations.add(station)
            }
            _stations.postValue(Resource.Success(tempStations))
        }
    }

    private fun checkMissionComplated(): Boolean {
        val stations = _stations.value
        return if (stations is Resource.Success) {
            val size = stations.data.filter { !it.isCurrentStation }.size
            if (size == 0) {
                updateCurrentStationToWorld()
                return true
            } else false
        } else false
    }


    private val _addFavoriteStation = SingleLiveEvent<Resource<Station>>()
    val addFavoriteStation: LiveData<Resource<Station>>
        get() = _addFavoriteStation


    fun addFavoriteStation(station: Station) {
        CoroutineScope(Dispatchers.IO).launch {
            stationRepository.getStations().collect {
                if (it is Resource.Success) {
                    if (it.data.contains(station)) {
                        _addFavoriteStation.postValue(Resource.Error(Throwable("Gezegen zaten favorilerde ekli")))
                    } else if (_currentStation.value?.name != station.name) {
                        _addFavoriteStation.postValue(Resource.Error(Throwable("Bulunmadığınız gezegeni favorilere ekleyemezsiniz")))
                    } else {
                        stationRepository.insertStation(station)
                        _addFavoriteStation.postValue(Resource.Success(station))
                    }
                }
            }
        }
    }
}