package com.places.compose.data.model

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Entity(tableName = "location")
data class CoordinatesBO(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey val id: Int = 0
) {
    internal constructor(latLng: LatLng): this(
        latitude = latLng.latitude,
        longitude = latLng.longitude
    )

    internal constructor(location: Location): this(
        latitude = location.latitude,
        longitude = location.longitude
    )

    private fun toLatLng() = LatLng(latitude, longitude)

    internal fun getBoundingRectangle(radiusMeters: Int): LatLngBounds {
        val latLng = toLatLng()
        val distanceFromCenterToCorner = radiusMeters * sqrt(2.0)
        val southwestCorner = SphericalUtil.computeOffset(latLng, distanceFromCenterToCorner, 225.0)
        val northeastCorner = SphericalUtil.computeOffset(latLng, distanceFromCenterToCorner, 45.0)
        return LatLngBounds(southwestCorner, northeastCorner)
    }

    fun calculateDistance(other: CoordinatesBO): Int {
        return SphericalUtil.computeDistanceBetween(this.toLatLng(), other.toLatLng()).roundToInt()
    }
}