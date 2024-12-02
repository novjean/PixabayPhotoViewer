package com.novatech.pixabayphotoviewer.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Utility class for securely storing and retrieving user credentials.
 */
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

    /**
     * Saves the user's email securely.
     *
     * @param context The Android context.
     * @param email The email to save.
     */
    fun saveEmail(context: Context, email: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    /**
     * Retrieves the saved email address securely from EncryptedSharedPreferences.
     *
     * @param context The application context used to access SharedPreferences.
     * @return The saved email address as a [String], or null if not found.
     */
    fun getEmail(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_EMAIL, null)
    }

    /**
     * Clears stored user credentials.
     *
     * @param context The Android context.
     */
    fun clearCredentials(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().clear().apply()
    }
}