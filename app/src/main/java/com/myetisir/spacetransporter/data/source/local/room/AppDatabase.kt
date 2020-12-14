package com.myetisir.spacetransporter.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myetisir.spacetransporter.data.model.entity.StationEntity
import com.myetisir.spacetransporter.data.model.entity.TransporterEntity

@Database(entities = [StationEntity::class, TransporterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stationDao(): StationDao
    abstract fun transporterDao(): TransporterDao
}