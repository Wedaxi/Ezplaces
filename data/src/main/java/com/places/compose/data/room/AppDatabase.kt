package com.places.compose.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.FavoriteBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.data.model.TimeStampBO

@Database(
    entities = [PlaceBO::class, FavoriteBO::class, TimeStampBO::class, CoordinatesBO::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.DeleteCoordinatesMigration::class)
    ]
)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
    abstract fun favoriteDAO(): FavoriteDAO
    abstract fun timestampDAO(): TimeStampDAO
    abstract fun locationDAO(): CoordinatesDAO

    @DeleteColumn(tableName = "place", columnName = "coordinates")
    class DeleteCoordinatesMigration : AutoMigrationSpec
}
