package com.myetisir.spacetransporter.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myetisir.spacetransporter.data.model.Transporter

@Entity(tableName = "Transporter")
data class TransporterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var name: String,
    var durability: Int,
    var speed: Int,
    var capacity: Int
) {

    fun toObject(): Transporter {
        return Transporter(name, durability, speed, capacity)
    }
}
