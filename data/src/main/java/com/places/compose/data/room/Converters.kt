package com.places.compose.data.room

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter

internal class Converters {

    @TypeConverter
    fun uriToString(value: Uri?) = value?.toString()

    @TypeConverter
    fun stringToUri(value: String?) = value?.toUri()
}