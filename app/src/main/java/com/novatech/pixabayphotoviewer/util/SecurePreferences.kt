package com.novatech.pixabayphotoviewer.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecurePreferences {

    private const val PREFS_NAME = "secure_prefs"
    private const val KEY_EMAIL = "user_email"

    private fun getSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveEmail(context: Context, email: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_EMAIL, null)
    }

    fun clearCredentials(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().clear().apply()
    }
}