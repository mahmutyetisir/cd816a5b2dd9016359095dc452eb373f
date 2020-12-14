package com.myetisir.spacetransporter.data.model

import com.myetisir.spacetransporter.data.model.entity.TransporterEntity

data class Transporter(var name: String, var durability: Int, var speed: Int, var capacity: Int) {

    fun toEntity(): TransporterEntity {
        return TransporterEntity(
            name = name,
            durability = durability,
            speed = speed,
            capacity = capacity
        )
    }
}
