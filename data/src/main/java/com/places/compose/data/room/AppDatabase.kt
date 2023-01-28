package com.places.compose.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.FavoriteBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.data.model.TimeStampBO

@Database(entities = [PlaceBO::class, FavoriteBO::class, TimeStampBO::class, CoordinatesBO::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
    abstract fun favoriteDAO(): FavoriteDAO
    abstract fun timestampDAO(): TimeStampDAO
    abstract fun locationDAO(): CoordinatesDAO
}
