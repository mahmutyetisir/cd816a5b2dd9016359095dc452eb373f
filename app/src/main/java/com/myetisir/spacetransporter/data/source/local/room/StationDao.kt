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
    suspend fun insertChannel(station: StationEntity)
}