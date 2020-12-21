package com.myetisir.spacetransporter.data.model

import com.myetisir.spacetransporter.data.model.entity.StationEntity

data class Station(
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    var stock: Int,
    var need: Int
) {
    var eus: Int? = null
    var isCurrentStation = false

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
