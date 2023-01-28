package com.places.compose.ui.main.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.places.compose.data.model.CreditBO
import timber.log.Timber

private const val CREDITS_FILE = "credits.json"

class CreditsViewModel(
    app: Application
): AndroidViewModel(app) {

    var credits by mutableStateOf<List<CreditBO>>(emptyList())
        private set

    init {
        runCatching {
            val inputStream = app.assets.open(CREDITS_FILE)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        }.onSuccess {
            val type = object : TypeToken<List<CreditBO>>() {}.type
            credits = Gson().fromJson(it, type)
        }.onFailure {
            Timber.e(it)
        }
    }
}