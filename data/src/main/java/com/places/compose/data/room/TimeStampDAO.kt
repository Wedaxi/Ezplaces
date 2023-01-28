package com.places.compose.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.places.compose.data.model.TimeStampBO

@Dao
internal interface TimeStampDAO {

    @Query("SELECT * FROM timeStamp WHERE id LIKE :id")
    suspend fun get(id: String): TimeStampBO?

    @Insert
    suspend fun insert(vararg timeStamp: TimeStampBO)

    @Delete
    suspend fun delete(vararg timeStamp: TimeStampBO)
}