package com.places.compose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
internal data class FavoriteBO(
    @PrimaryKey val id: String
)