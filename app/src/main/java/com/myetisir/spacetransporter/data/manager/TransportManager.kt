package com.myetisir.spacetransporter.data.manager

import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.model.TravelError
import com.myetisir.spacetransporter.data.model.TravelMission
import com.myetisir.spacetransporter.data.model.TravelStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.abs

interface TransportManager {
    suspend fun startTransport(mission: TravelMission): Flow<TravelStatus<TravelMission>>
    fun calculateNeedEUS(currentStation: Station, destinationStation: Station): Int
}

class TransportManagerImpl @Inject constructor() : TransportManager {

    override suspend fun startTransport(mission: TravelMission): Flow<TravelStatus<TravelMission>> {
        return flow {
            emit(TravelStatus.Loading())
            val travelEUS = calculateNeedEUS(mission.currentStation, mission.destinationStation)
            when {
                mission.transporter.capacity < mission.destinationStation.need -> {
                    emit(TravelStatus.Error<TravelMission>(TravelError.CapacityError))
                }
                mission.transporter.speed < travelEUS -> {
                    emit(TravelStatus.Error<TravelMission>(TravelError.EUSError))
                }
                mission.transporterCrashRate <= 0 -> {
                    emit(TravelStatus.Error<TravelMission>(TravelError.CrashError))
                }
                else -> {
                    mission.transporter.capacity =
                        mission.transporter.capacity - mission.destinationStation.need
                    mission.destinationStation.need = 0
                    mission.transporter.speed = mission.transporter.speed - travelEUS
                    emit(TravelStatus.Success(mission))
                }
            }
        }
    }

    override fun calculateNeedEUS(currentStation: Station, destinationStation: Station): Int {
        return abs(currentStation.coordinateX.toInt() - destinationStation.coordinateX.toInt()) + abs(
            currentStation.coordinateY.toInt() - destinationStation.coordinateY.toInt()
        )
    }
}