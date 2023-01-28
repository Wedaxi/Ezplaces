package com.places.compose.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.places.compose.data.model.CoordinatesBO

@Dao
internal interface CoordinatesDAO {

    @Query("SELECT * FROM location WHERE id LIKE 0")
    suspend fun get(): CoordinatesBO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coordinates: CoordinatesBO)
}