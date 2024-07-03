package com.places.compose.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place

@Entity(tableName = "place")
data class PlaceBO(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String? = null,
    @Embedded(prefix = "loc_") val coordinates: CoordinatesBO? = null,
    @ColumnInfo(name = "icon") val icon: String? = null,
    @ColumnInfo(name = "iconBackground") val iconBackground: Int? = null,
    @ColumnInfo(name = "phone") val phone: String? = null,
    @Ignore val rating: Double? = null,
    @Ignore val numRatings: Int = 0,
    @ColumnInfo(name = "webSite") val webSite: Uri? = null,
    @Ignore val plusCode: String? = null,
    @Ignore val schedule: List<String> = emptyList(),
    @Ignore internal val types: List<String> = emptyList(),
    @Ignore internal val photoMetadata: List<PhotoMetadata> = emptyList()
) {

    val hasPhotoMetadata: Boolean
        get() = photoMetadata.isNotEmpty()

    internal constructor(place: Place): this(
        id = place.id.orEmpty(),
        name = place.name.orEmpty(),
        address = place.address,
        coordinates = place.latLng?.let { CoordinatesBO(it) },
        icon = place.iconUrl,
        iconBackground = place.iconBackgroundColor,
        phone = place.phoneNumber,
        rating = place.rating,
        numRatings = place.userRatingsTotal ?: 0,
        webSite = place.websiteUri,
        plusCode = place.plusCode?.globalCode,
        schedule = place.openingHours?.weekdayText.orEmpty(),
        types = place.placeTypes.orEmpty(),
        photoMetadata = place.photoMetadatas.orEmpty()
    )

    internal constructor(prediction: AutocompletePrediction): this(
        id = prediction.placeId,
        name = prediction.getFullText(null).toString(),
        types = prediction.types.orEmpty()
    )

    internal constructor(
        id: String,
        name: String,
        coordinates: CoordinatesBO? = null,
        address: String? = null,
        icon: String? = null,
        iconBackground: Int? = null,
        phone: String? = null,
        webSite: Uri? = null
    ): this(
        id = id,
        name = name,
        coordinates = coordinates,
        address = address,
        icon = icon,
        iconBackground = iconBackground,
        phone = phone,
        webSite = webSite,
        photoMetadata = emptyList()
    )
}