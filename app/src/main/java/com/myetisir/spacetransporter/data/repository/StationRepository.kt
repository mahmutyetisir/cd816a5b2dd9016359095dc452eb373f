package com.myetisir.spacetransporter.data.repository

import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.source.local.SpaceTransporterLocalDataSource
import com.myetisir.spacetransporter.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface StationRepository {

    suspend fun insertStation(station: Station)

    suspend fun getStations(): Flow<Resource<List<Station>>>

    suspend fun deleteStation(stationName: String)
}

class StationRepositoryImpl @Inject constructor(private val localDataSource: SpaceTransporterLocalDataSource) :
    StationRepository {

    override suspend fun insertStation(station: Station) {
        localDataSource.insertStation(station)
    }

    override suspend fun getStations(): Flow<Resource<List<Station>>> {
        return flow {
            emit(Resource.Loading)
            val station = localDataSource.getAllStation()
            emit(Resource.Success(station))
        }
    }

    override suspend fun deleteStation(stationName: String) {
        localDataSource.deleteStation(stationName)
    }


}