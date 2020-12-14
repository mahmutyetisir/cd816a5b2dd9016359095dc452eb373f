package com.myetisir.spacetransporter.data.model

import com.myetisir.spacetransporter.data.model.entity.StationEntity

data class Station(
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    val stock: Int,
    val need: Int
) {

    fun toEntity(): StationEntity {
        return StationEntity(
            name = name,
            coordinateX = coordinateX,
            coordinateY = coordinateY,
            capacity = capacity,
            stock = stock,
            need = need
        )
    }
}
