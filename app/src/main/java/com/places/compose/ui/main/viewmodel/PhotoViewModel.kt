package com.places.compose.ui.main.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.places.compose.data.model.PlaceBO
import com.places.compose.usecase.GetPhotoUseCase

class PhotoViewModel(
    place: PlaceBO,
    getPhotoUseCase: GetPhotoUseCase
): ViewModel() {

    var photo by mutableStateOf<Bitmap?>(null)
        private set

    init {
        getPhotoUseCase.invoke(scope = viewModelScope, params = place) {
            photo = it
        }
    }

    override fun onCleared() {
        photo?.recycle()
        super.onCleared()
    }
}