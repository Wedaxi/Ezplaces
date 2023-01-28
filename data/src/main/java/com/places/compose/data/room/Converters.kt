package com.places.compose.data.room

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.places.compose.data.model.CoordinatesBO

internal class Converters {

    private val gson = Gson()

    @TypeConverter
    fun uriToString(value: Uri?) = value?.toString()

    @TypeConverter
    fun stringToUri(value: String?) = value?.let { Uri.parse(it) }

    @TypeConverter
    fun coordinatesToString(value: CoordinatesBO?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    fun stringToCoordinates(value: String?): CoordinatesBO? = value?.let {  gson.fromJson(it, CoordinatesBO::class.java) }
}