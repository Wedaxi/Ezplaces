package com.places.compose.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.places.compose.data.model.FavoriteBO
import com.places.compose.data.model.PlaceBO

@Dao
internal interface FavoriteDAO {

    @Query("SELECT * FROM place WHERE id IN (SELECT * FROM favorite)")
    suspend fun getFavorites(): List<PlaceBO>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id LIKE :id)")
    suspend fun findById(id: String): Boolean

    @Insert
    suspend fun insertAll(vararg places: FavoriteBO)

    @Delete
    suspend fun delete(vararg place: FavoriteBO)
}