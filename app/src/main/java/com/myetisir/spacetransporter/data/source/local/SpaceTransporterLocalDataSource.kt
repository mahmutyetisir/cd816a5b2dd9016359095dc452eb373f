package com.myetisir.spacetransporter.data.source.local

import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.model.Transporter
import com.myetisir.spacetransporter.data.source.local.room.StationDao
import com.myetisir.spacetransporter.data.source.local.room.TransporterDao
import javax.inject.Inject

interface SpaceTransporterLocalDataSource {
    suspend fun insertTransporter(transporter: Transporter)

    suspend fun getTransporter(): Transporter?

    suspend fun insertStation(station: Station)

    suspend fun getAllStation(): List<Station>
}

class SpaceTransporterLocalDataSourceImpl @Inject constructor(
    private val transporterDao: TransporterDao,
    private val stationDao: StationDao
) : SpaceTransporterLocalDataSource {

    override suspend fun insertTransporter(transporter: Transporter) {
        transporterDao.insetTransporter(transporter.toEntity())
    }

    override suspend fun getTransporter(): Transporter? {
        return transporterDao.getTransporter()?.toObject()
    }

    override suspend fun insertStation(station: Station) {
        stationDao.insertChannel(station.toEntity())
    }

    override suspend fun getAllStation(): List<Station> {
        val list = ArrayList<Station>()
        stationDao.getAllStation().forEach { currentStation ->
            list.add(currentStation.toObject())
        }
        return list
    }

}