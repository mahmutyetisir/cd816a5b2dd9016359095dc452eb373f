package com.myetisir.spacetransporter.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myetisir.spacetransporter.data.model.Station

@Entity(tableName = "Stations")
data class StationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    val stock: Int,
    val need: Int
) {

    fun toObject(): Station {
        return Station(name, coordinateX, coordinateY, capacity, stock, need)
    }
}