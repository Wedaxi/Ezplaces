package com.places.compose.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO

class AutoCompleteActivityContract: ActivityResultContract<CoordinatesBO?, PlaceBO?>() {

    override fun createIntent(context: Context, input: CoordinatesBO?): Intent =
        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, listOf(Place.Field.ID))
            .setLocationRestriction(input?.getBoundingRectangle(500)?.let { RectangularBounds.newInstance(it) })
            .build(context)

    override fun parseResult(resultCode: Int, intent: Intent?): PlaceBO? = runCatching {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            PlaceBO(Autocomplete.getPlaceFromIntent(intent))
        } else {
            null
        }
    }.getOrNull()
}