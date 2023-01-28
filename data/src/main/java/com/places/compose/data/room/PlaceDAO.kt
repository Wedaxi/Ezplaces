package com.places.compose.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.places.compose.data.model.PlaceBO

@Dao
internal interface PlaceDAO {

    @Query("SELECT * FROM place")
    suspend fun getPlaces(): List<PlaceBO>

    @Query("SELECT * FROM place WHERE id LIKE :id")
    suspend fun get(id: String): PlaceBO?

    @Query("SELECT EXISTS(SELECT * FROM place WHERE id LIKE :id)")
    suspend fun findById(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg places: PlaceBO)

    @Update
    suspend fun updatePlace(place: PlaceBO)

    @Delete
    suspend fun delete(vararg places: PlaceBO)

}