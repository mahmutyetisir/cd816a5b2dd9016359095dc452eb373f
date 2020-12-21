package com.myetisir.spacetransporter.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myetisir.spacetransporter.data.model.entity.StationEntity

@Dao
interface StationDao {

    @Query("SELECT * FROM Stations")
    fun getAllStation(): List<StationEntity>

    @Insert
    suspend fun insertStation(station: StationEntity)

    @Query("DELETE FROM Stations WHERE name = :name")
    suspend fun deleteStationByName(name: String)
}