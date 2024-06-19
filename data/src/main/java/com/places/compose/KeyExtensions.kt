package com.places.compose

import android.util.Base64
import com.cossacklabs.themis.SecureCell
import com.places.compose.data.BuildConfig

private val generatedKey: ByteArray
    get() {
        val rawKey = buildString(5) {
            append(byteArrayOf(0x12, 0x27, 0x42).base64EncodedString())
            append(500 + 6 / 7 * 89)
            append(BuildConfig.ENCRYPTION_KEY)
        }
        return rawKey.toByteArray()
    }

private fun ByteArray.base64EncodedString(): String = Base64.encodeToString(this, Base64.NO_WRAP)

internal fun String.encrypt() = SecureCell.SealWithKey(generatedKey).encrypt(this.toByteArray()).base64EncodedString()

internal fun String.decrypt() = runCatching {
    val encryptedData = Base64.decode(this, Base64.NO_WRAP)
    val decodedString = SecureCell.SealWithKey(generatedKey).decrypt(encryptedData)
    String(decodedString)
}.getOrNull()