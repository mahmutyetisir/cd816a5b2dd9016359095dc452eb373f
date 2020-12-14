package com.myetisir.spacetransporter.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myetisir.spacetransporter.data.model.entity.TransporterEntity

@Dao
interface TransporterDao {

    @Query("SELECT * FROM Transporter")
    fun getTransporter(): TransporterEntity?

    @Insert
    suspend fun insetTransporter(transporter: TransporterEntity)
}