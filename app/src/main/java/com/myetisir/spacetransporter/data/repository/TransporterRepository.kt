package com.myetisir.spacetransporter.data.repository

import com.myetisir.spacetransporter.data.model.Transporter
import com.myetisir.spacetransporter.data.source.local.SpaceTransporterLocalDataSource
import com.myetisir.spacetransporter.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface TransporterRepository {

    suspend fun insertTransporter(transporter: Transporter)

    suspend fun getTransporter(): Flow<Resource<Transporter>>
}

class TransporterRepositoryImpl @Inject constructor(private val localDataSource: SpaceTransporterLocalDataSource) :
    TransporterRepository {

    override suspend fun insertTransporter(transporter: Transporter) {
        localDataSource.insertTransporter(transporter)
    }

    override suspend fun getTransporter(): Flow<Resource<Transporter>> {
        return flow {
            emit(Resource.Loading)
            val transporter = localDataSource.getTransporter()
            if (transporter == null) emit(Resource.Error(Throwable("Kayıtlı bir geminiz yok")))
            else emit(Resource.Success(transporter))
        }
    }
}