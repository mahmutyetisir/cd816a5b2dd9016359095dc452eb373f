package com.myetisir.spacetransporter.ui.fragment.create

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myetisir.spacetransporter.data.model.Transporter
import com.myetisir.spacetransporter.data.repository.TransporterRepository
import com.myetisir.spacetransporter.util.Resource
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTransporterViewModel @ViewModelInject constructor(private val transporterRepository: TransporterRepository) :
    BaseViewModel() {
    private val UGS = 10000
    private val EUS = 20
    private val DS = 10000

    private val _transporter = MutableLiveData<Transporter>()
    val transporter: LiveData<Transporter>
        get() = _transporter

    private val _points = MutableLiveData(15)
    val points: LiveData<Int>
        get() = _points

    private val _navigateHome = MutableLiveData<Resource<Boolean>>()
    val navigateHome: LiveData<Resource<Boolean>>
        get() = _navigateHome

    var name: String = ""

    var tempDurability = Point(0)
    var tempSpeed = Point(0)
    var tempCapacity = Point(0)

    fun updateTransporterName(name: String) {
        this.name = name
    }

    fun updateTransporterDurability(durability: Point): Transporter {
        if (durability.isIncrease) {
            if ((15 - getSumOfPoints()) < 0) {
                for (count: Int in 1..(getSumOfPoints() - 15)) {
                    if (tempSpeed.data > 0) tempSpeed.data--
                    else if (tempCapacity.data > 0) tempCapacity.data--
                }
            }
        }
        return calculatePoints()
    }

    fun updateTransporterSpeed(speed: Point): Transporter {
        if (speed.isIncrease) {
            if ((15 - getSumOfPoints()) < 0) {
                for (count: Int in 1..(getSumOfPoints() - 15)) {
                    if (tempDurability.data > 0) tempDurability.data--
                    else if (tempCapacity.data > 0) tempCapacity.data--
                }
            }
        }
        return calculatePoints()
    }

    fun updateTransporterCapacity(capacity: Point): Transporter {
        if (capacity.isIncrease) {
            if ((15 - getSumOfPoints()) < 0) {
                for (count: Int in 1..(getSumOfPoints() - 15)) {
                    if (tempDurability.data > 0) tempDurability.data--
                    else if (tempSpeed.data > 0) tempSpeed.data--
                }
            }
        }
        return calculatePoints()
    }

    fun checkTransporterIsCreated() {
        viewModelScope.launch(Dispatchers.IO) {
            transporterRepository.getTransporter().collect {
                if (it is Resource.Success) {
                    withContext(Dispatchers.Main) {
                        _navigateHome.value = Resource.Success(true)
                    }
                }
            }
        }
    }

    fun contiune() {
        when {
            name.isEmpty() -> {
                _navigateHome.value = Resource.Error(Throwable("İsimi boş geçemezsiniz"))
            }
            getSumOfPoints() < 15 -> {
                _navigateHome.value =
                    Resource.Error(Throwable("Puanlarınızın hepsini kullanmadınız"))
            }
            else -> {
                viewModelScope.launch {
                    transporterRepository.insertTransporter(
                        Transporter(
                            name,
                            tempDurability.data * DS,
                            tempSpeed.data * EUS,
                            tempCapacity.data * UGS
                        )
                    )
                }
                _navigateHome.value = Resource.Success(true)
            }
        }
    }


    private fun calculatePoints(): Transporter {
        val tempTransporter =
            Transporter(name, tempDurability.data, tempSpeed.data, tempCapacity.data)
        _transporter.value = tempTransporter
        _points.value = 15 - getSumOfPoints()
        return tempTransporter
    }

    private fun getSumOfPoints(): Int = tempDurability.data + tempSpeed.data + tempCapacity.data

}

data class Point(var data: Int) {
    var isIncrease: Boolean = true

    fun updatePoint(newValue: Int): Point {
        isIncrease = newValue > data
        data = newValue
        return this
    }
}