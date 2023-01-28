package com.places.compose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeStamp")
internal data class TimeStampBO (
    @PrimaryKey val id: String,
    val millis: Long
)