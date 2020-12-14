package com.myetisir.spacetransporter.data.repository

import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.repository.ssot.resultFlow
import com.myetisir.spacetransporter.data.source.remote.SpaceTransporterApiDataSource
import com.myetisir.spacetransporter.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MainRepository {

    suspend fun getStation(): Flow<Resource<List<Station>>>
}

class MainRepositoryImpl @Inject constructor(private val remoteDataSource: SpaceTransporterApiDataSource) :
    MainRepository {

    override suspend fun getStation(): Flow<Resource<List<Station>>> {
        return resultFlow {
            remoteDataSource.getStations()
        }
    }

}

