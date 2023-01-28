package com.places.compose

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.ResolveInfoFlags
import android.net.Uri
import android.os.Build
import androidx.navigation.NavController
import com.places.compose.util.IntentBuilder
import java.util.Calendar
import java.util.Locale
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun IntArray.rotateFrom(value: Int) = rotate(indexOf(value))

fun IntArray.rotate(index: Int) = drop(index) + take(index)

private val Calendar.orderedWeekDays
    get() = intArrayOf(
        Calendar.MONDAY,
        Calendar.TUESDAY,
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY,
        Calendar.SATURDAY,
        Calendar.SUNDAY
    ).rotateFrom(firstDayOfWeek)

private val Calendar.currentDay
    get() = get(Calendar.DAY_OF_WEEK)

private fun Calendar.getLocaleName(day: Int): String {
    val locale = Locale.getDefault()
    set(Calendar.DAY_OF_WEEK, day)
    return getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)?.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(locale)
            } else {
                it.toString()
            }
        }.orEmpty()
}

val Calendar.currentDayName
    get() = getLocaleName(currentDay)

val Calendar.orderedWeekDaysNames
    get() = orderedWeekDays.map { getLocaleName(it) }

fun NavController.goToDetail(id: String) = navigate("detail/$id")

@OptIn(ExperimentalContracts::class)
inline fun buildUri(builderAction: Uri.Builder.() -> Unit): Uri {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    val builder = Uri.Builder()
    builder.builderAction()
    return builder.build()
}

@Suppress("DEPRECATION")
fun Context.startMapsOrPlayStore(intent: Intent) {
    val list = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(intent, 0)
    } else {
        packageManager.queryIntentActivities(intent, ResolveInfoFlags.of(0))
    }
    if (list.isEmpty()) {
        startActivity(IntentBuilder.playStoreIntent(IntentBuilder.MAPS_PACKAGE))
    } else {
        startActivity(intent)
    }
}